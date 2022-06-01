package com.example.movies.api

import com.example.movies.database.tables.MovieDetail
import com.example.movies.models.MovieDetailResponse
import com.example.movies.models.SearchResult
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface MovieService {
    // Request method and URL specified in the annotation
    @GET(".")
    fun getMovieDetail(@Query("i") movieName: String): Call<MovieDetailResponse>

    @GET(".")
    fun search(@Query("s") query: String): Call<SearchResult>


    companion object {

        val API_KEY: String = "apikey"
        private const val BASE_URL = "https://www.omdbapi.com/"
        var retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        fun create(okHttpClient: OkHttpClient): MovieService =
            retrofitBuilder
                .client(okHttpClient)
                .build()
                .create(MovieService::class.java)

    }
}
