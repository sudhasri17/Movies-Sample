/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.movies.ui.search

import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.*
import android.widget.CursorAdapter
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.movies.R
import com.example.movies.database.tables.MovieTileTable
import com.example.movies.databinding.FragmentSearchBinding
import com.example.movies.di.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    private lateinit var binding: FragmentSearchBinding


    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SearchViewModel by viewModels { viewModelFactory }
    private lateinit var movieListAdapter: MovieListAdapter
    private var searchView: SearchView? = null
    private lateinit var cursorAdapter: SimpleCursorAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        setupToolbar()
        setupDataView()
        subscribeToUi()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        val searchMenu = menu.findItem(R.id.app_bar_search)
        searchView = searchMenu?.actionView as SearchView?
        setupSearchView()
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(
            item
        )
    }

    override fun onPause() {
        dismissKeyboard()
        super.onPause()
    }


    private fun setupDataView() {
        movieListAdapter = MovieListAdapter(true,
            movieTileClickListener = { movieId ->
                findNavController().navigate(
                    SearchFragmentDirections
                        .actionSearchFragmentToMovieDetailFragment(movieId)
                )
            },
            favClickListener = { movieTileTable ->
                val updatedMovieTile = MovieTileTable()
                updatedMovieTile.also {
                    it.imdbID = movieTileTable.imdbID
                    it.year = movieTileTable.year
                    it.title = movieTileTable.title
                    it.poster = movieTileTable.poster
                    it.isFav = !movieTileTable.isFav
                }
                viewModel.saveToFavorites(updatedMovieTile)
                updatedMovieTile
            })

        binding.recyclerView.apply {
            adapter = movieListAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun subscribeToUi() {
        viewModel.searchResults.observe(viewLifecycleOwner) {
            movieListAdapter.updateList(it)
        }

        viewModel.recentSearches.observe(viewLifecycleOwner) {
            val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
            it.forEachIndexed { index, suggestion ->
                cursor.addRow(arrayOf(index, suggestion))
            }

            cursorAdapter.changeCursor(cursor)
        }
    }

    private fun setupSearchView() {
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        cursorAdapter = SimpleCursorAdapter(
            context, R.layout.search_item,
            null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )

        searchView?.apply {
            queryHint = getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    Log.d("SEARCH","onQueryTextSubmit")
                    dismissKeyboard()
                    viewModel.onSearchQuerySubmitted(query)
                    searchView?.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.onSearchQueryChanged(newText)
                    return true
                }
            })
        }

        searchView?.suggestionsAdapter = cursorAdapter
        searchView?.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                Log.d("SEARCH","onSuggestionClick")
                dismissKeyboard()
                val cursor = searchView?.suggestionsAdapter?.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                searchView?.setQuery(selection, true)
                return true
            }

        })
    }

    private fun setupToolbar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.title = getString(R.string.app_name)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }

    private fun dismissKeyboard() {
        searchView?.let {
            ViewCompat.getWindowInsetsController(it)?.hide(WindowInsetsCompat.Type.ime()) }
    }
}
