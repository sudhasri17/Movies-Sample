package com.example.movies.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.FavoriteRepository
import com.example.movies.data.LoadDataCallback
import com.example.movies.data.SearchRepository
import com.example.movies.database.tables.MovieTileTable
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    val favoriteRepository: FavoriteRepository
    ) : ViewModel() {

    private var _searchResults: MutableLiveData<List<MovieTileTable>> = MutableLiveData()
    val searchResults: LiveData<List<MovieTileTable>> = _searchResults

    private var _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private var _errorView: MutableLiveData<Boolean> = MutableLiveData()
    val errorView: LiveData<Boolean> = _errorView

    private var _recentSearches: MutableLiveData<List<String>> = MutableLiveData()
    val recentSearches: LiveData<List<String>> = _recentSearches

    private var _placeholder: MutableLiveData<Boolean> = MutableLiveData()
    val placeholder: LiveData<Boolean> = _placeholder

    var searchJob: Job? = null

    init {
        _placeholder.value = true
    }

    private fun getSearchResults(query: String) {
        Log.d("SEARCH","getSearchResults")
        _loading.value = true
        _placeholder.value = false
        searchRepository.getSearchResults(query, object:LoadDataCallback<List<MovieTileTable>>{
            override fun onDataLoaded(data: List<MovieTileTable>) {
                Log.d("SEARCH","Data loaded in viewmodel")
                _loading.value = false
                _errorView.value = false
                _searchResults.value = data
            }

            override fun onDataNotAvailable(error: String) {
                _loading.value = false
                _errorView.value = true
            }
        })
    }

    private fun getRecentSearches(query: String) {
        searchRepository.getRecentSearches(query, object:LoadDataCallback<List<String>>{
            override fun onDataLoaded(data: List<String>) {
                _recentSearches.value = data
            }

            override fun onDataNotAvailable(error: String) {
                //NO-OP
            }
        })
    }
    fun onSearchQueryChanged(newText: String?) {
        searchJob = viewModelScope.launch {
            searchJob?.cancel()
            newText?.let {
                delay(500)
                if (it.isEmpty()) {
                    resetSearch()
                } else {
                    getRecentSearches(it)
                }
            }
        }
    }

    private fun resetSearch() {
        searchJob?.cancel()
    }

    fun saveToFavorites(updatedMovieTile: MovieTileTable) {
        favoriteRepository.addAsFavoriteMovie(updatedMovieTile)
    }

    fun onSearchQuerySubmitted(query: String) {
        Log.d("SEARCH", "onSearchQuerySubmitted")
        getSearchResults(query)
    }

}