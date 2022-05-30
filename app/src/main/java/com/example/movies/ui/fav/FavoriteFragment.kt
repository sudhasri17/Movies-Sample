package com.example.movies.ui.fav

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.movies.databinding.FragmentFavoriteBinding
import com.example.movies.ui.search.MovieListAdapter

/**
 * A fragment representing a list of Items.
 */
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val adapter = MovieListAdapter()
        binding.list.adapter = adapter

        subscribeUi(adapter, binding)
        return binding.root
    }

    private fun subscribeUi(adapter: MovieListAdapter, binding: FragmentFavoriteBinding) {
        viewModel.favMovies.observe(viewLifecycleOwner) { result ->
            binding.hasResults = result.isNotEmpty()
            adapter.updateList(result)
        }
    }


}