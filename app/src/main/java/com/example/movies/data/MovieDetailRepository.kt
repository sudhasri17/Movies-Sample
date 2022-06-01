package com.example.movies.data

import com.example.movies.database.tables.MovieDetail
import com.example.movies.di.LocalDataSource
import com.example.movies.di.RemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailRepository @Inject constructor(
    @LocalDataSource val localMovieDetailDataSource: MovieDetailDataSource,
    @RemoteDataSource val remoteMovieDetailDataSource: MovieDetailDataSource) {

    fun fetchMovieDetail(movieId: String, callback: LoadDataCallback<MovieDetail>){

        //First check if data is available in DB
        localMovieDetailDataSource.getMovieDetailTable(movieId, object: LoadDataCallback<MovieDetail>{
            override fun onDataLoaded(data: MovieDetail) {
                callback.onDataLoaded(data)
            }

            override fun onDataNotAvailable(error: String) {
                // fetch data from remote
                remoteMovieDetailDataSource.getMovieDetailTable(movieId, object: LoadDataCallback<MovieDetail>{
                    override fun onDataLoaded(data: MovieDetail) {
                        //save the data in DB
                        localMovieDetailDataSource.saveMovieDetail(data)
                        callback.onDataLoaded(data)
                    }

                    override fun onDataNotAvailable(error: String) {
                        callback.onDataNotAvailable(error)
                    }
                })
            }
        })
    }

}