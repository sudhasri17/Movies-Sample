package com.example.movies.ui.fav

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies.data.FavoriteRepository
import com.example.movies.data.LoadDataCallback
import com.example.movies.database.tables.MovieTileTable
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    private var _favMovies: MutableLiveData<List<MovieTileTable>> = MutableLiveData()
    val favMovies: LiveData<List<MovieTileTable>> = _favMovies

    private var _error: MutableLiveData<Boolean> = MutableLiveData()
    val error: LiveData<Boolean> = _error

    private var _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private var _placeholder: MutableLiveData<Boolean> = MutableLiveData()
    val placeholder: LiveData<Boolean> = _placeholder

    init {
        _placeholder.value = true
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        _loading.value = true
        _placeholder.value = false
        favoriteRepository.fetchFavoriteMovies(object : LoadDataCallback<List<MovieTileTable>> {

            override fun onDataLoaded(data: List<MovieTileTable>) {
                _loading.value = false
                _error.value = false
                _favMovies.value = data
            }

            override fun onDataNotAvailable(error: String) {
                _loading.value = false
                _error.value = true
            }
        })
    }
}