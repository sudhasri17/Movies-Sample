package com.example.movies.ui.fav

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies.data.FavoriteRepository
import com.example.movies.data.FavoriteResponseListener
import com.example.movies.database.tables.MovieTileTable
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository) :
    ViewModel() {

    private var _favMovies: MutableLiveData<List<MovieTileTable>> = MutableLiveData()
    val favMovies: LiveData<List<MovieTileTable>> = _favMovies

    init {
        val movie = MovieTileTable()
        movie.title = "Good Job"
        movie.imdbID = "ssdca"
        movie.year = "2022"
        movie.isFav = true

        favoriteRepository.addAsFavoriteMovie(movie)
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        favoriteRepository.fetchFavoriteMovies(object : FavoriteResponseListener {
            override fun onResponse(favorites: List<MovieTileTable>) {
                _favMovies.value = favorites
            }
        })
    }
}