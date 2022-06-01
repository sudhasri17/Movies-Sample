package com.example.movies.ui.movieDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies.data.LoadDataCallback
import com.example.movies.data.MovieDetailRepository
import com.example.movies.database.tables.MovieDetail
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    val movieDetailRepository: MovieDetailRepository) : ViewModel() {

    private var _movieDetail : MutableLiveData<MovieDetail> = MutableLiveData()
    val movieDetail: LiveData<MovieDetail> = _movieDetail

    private var _loading : MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private var _errorView: MutableLiveData<Boolean> = MutableLiveData()
    val errorView: LiveData<Boolean> = _errorView

    fun getMovieDetail(movieId: String?) {
        _loading.value = true
        if(movieId == null || movieId.isEmpty()) {
            _loading.value = false
            _errorView.value = true
        } else {
            movieDetailRepository.fetchMovieDetail(movieId, object: LoadDataCallback<MovieDetail>{
                override fun onDataLoaded(data: MovieDetail) {
                    _loading.value = false
                    _movieDetail.value = data
                }
                override fun onDataNotAvailable(error: String) {
                    _loading.value = false
                    _errorView.value = true
                }
            })
        }
    }
}