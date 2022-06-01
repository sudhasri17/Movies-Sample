package com.example.movies.ui.fav

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.movies.R
import com.example.movies.databinding.FragmentFavoriteBinding
import com.example.movies.di.ViewModelFactory
import com.example.movies.ui.search.MovieListAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
class FavoriteFragment : DaggerFragment() {

    private lateinit var binding: FragmentFavoriteBinding
    @Inject
    lateinit var viewModelFactory:ViewModelFactory
    private val viewModel: FavoriteViewModel by viewModels {viewModelFactory}
    private lateinit var adapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MovieListAdapter(false,
            { movieId ->
                findNavController()
                    .navigate(
                        FavoriteFragmentDirections
                            .actionFavoriteFragmentToMovieDetailFragment2(movieId)
                    )
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            list.adapter = adapter
        }

        subscribeUi()
        return binding.root
    }


    private fun subscribeUi() {
        viewModel.favMovies.observe(viewLifecycleOwner) { result ->
            adapter.updateList(result)
            binding.executePendingBindings()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.viewModel = viewModel
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.title = getString(R.string.fav_movies)
    }
}