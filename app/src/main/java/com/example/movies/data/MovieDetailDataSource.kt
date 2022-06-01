package com.example.movies.data

import com.example.movies.BuildConfig
import com.example.movies.api.MovieService
import com.example.movies.database.tables.MovieDetail
import com.example.movies.database.tables.MovieTileTable_Table
import com.example.movies.models.MovieDetailResponse
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.request
import javax.inject.Inject
import javax.inject.Singleton

interface MovieDetailDataSource {
    fun getMovieDetailTable(movieId: String, dataCallback: LoadDataCallback<MovieDetail>)

    fun saveMovieDetail(movieDetail: MovieDetail)
}

@Singleton
class LocalMovieDetailDataSource : MovieDetailDataSource{
    override fun getMovieDetailTable(movieId: String, dataCallback: LoadDataCallback<MovieDetail>) {
        val movieDetail =  SQLite.select()
            .from(MovieDetail::class.java)
            .where(MovieTileTable_Table.imdbID.eq(movieId))
            .querySingle()

        if (movieDetail != null) {
            dataCallback.onDataLoaded(movieDetail)
        } else {
            dataCallback.onDataNotAvailable()
        }
    }

    override fun saveMovieDetail(movieDetail: MovieDetail) {
        movieDetail.save()
    }

}

@Singleton
class RemoteMovieDetailDataSource @Inject constructor(val movieService: MovieService): MovieDetailDataSource {
    override fun getMovieDetailTable(movieId: String, dataCallback: LoadDataCallback<MovieDetail>){
       movieService.getMovieDetail(movieId).request {
           response ->
           when (response){
               is ApiResponse.Success<MovieDetailResponse> -> {
                   val movieDetail: MovieDetail = response.data.toMovieDetail()
                   dataCallback.onDataLoaded(movieDetail)
           }
               is ApiResponse.Failure.Error ->
                   dataCallback.onDataNotAvailable(response.errorBody.toString())
               is ApiResponse.Failure.Exception ->
                   dataCallback.onDataNotAvailable(response.exception.message?:"Data not available")
           }
       }
    }

    override fun saveMovieDetail(movieDetail: MovieDetail) {
        // NO_OP
    }
}