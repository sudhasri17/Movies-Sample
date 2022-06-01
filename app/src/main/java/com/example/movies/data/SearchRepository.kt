package com.example.movies.data

import com.example.movies.database.tables.MovieDetail
import com.example.movies.database.tables.MovieTileTable
import com.example.movies.di.LocalDataSource
import com.example.movies.di.RemoteDataSource
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val localSearchDataSource: LocalSearchDataSource,
    private val remoSearchDataSource: RemoteSearchDataSource) {

    fun getSearchResults(query: String, loadDataCallback: LoadDataCallback<List<MovieTileTable>>){
        //First check if data is available in DB
        localSearchDataSource.getSearchResults(query, object: LoadDataCallback<List<MovieTileTable>>{
            override fun onDataLoaded(data: List<MovieTileTable>) {
                loadDataCallback.onDataLoaded(data)
            }

            override fun onDataNotAvailable(error: String) {
                // fetch data from remote
                remoSearchDataSource.getSearchResults(query, object: LoadDataCallback<List<MovieTileTable>>{
                    override fun onDataLoaded(data: List<MovieTileTable>) {
                        //save the data in DB
                        localSearchDataSource.saveSearchResults(query, data)
                        loadDataCallback.onDataLoaded(data)
                    }

                    override fun onDataNotAvailable(error: String) {
                        loadDataCallback.onDataNotAvailable(error)
                    }
                })
            }
        })
    }

    fun getRecentSearches(query: String, loadDataCallback: LoadDataCallback<List<String>>) {
        localSearchDataSource.geRecentSearches(query, loadDataCallback)

    }
}