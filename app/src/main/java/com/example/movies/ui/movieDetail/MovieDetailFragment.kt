package com.example.movies.ui.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.movies.R
import com.example.movies.databinding.FragmentMovieDetailBinding
import com.example.movies.di.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MovieDetailFragment : DaggerFragment() {

    private  lateinit var binding: FragmentMovieDetailBinding
    @Inject lateinit var movieViewModelFactory:ViewModelFactory
    private val viewModel: MovieDetailViewModel by viewModels {movieViewModelFactory}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@MovieDetailFragment.viewModel
        }
        val movieId = arguments?.getString(ARGUMENT_MOVIE_ID, null)

        viewModel.getMovieDetail(movieId)
        viewModel.movieDetail.observe(viewLifecycleOwner){
            binding.detailImage.setImageURI(it.poster)
            binding.toolbar.title = it.title
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.title = ""
    }

    companion object {

        const val ARGUMENT_MOVIE_ID = "MOVIE_ID"

        fun newInstance(movieID: String) = MovieDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARGUMENT_MOVIE_ID, movieID)
            }
        }
    }

}