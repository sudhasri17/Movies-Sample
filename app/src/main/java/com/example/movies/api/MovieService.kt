package com.example.movies.api

import com.example.movies.models.MovieDetail
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface MovieService {
    // Request method and URL specified in the annotation
    @GET
    fun getMovieDetail(@Query("s") movieName: String): MovieDetail

    @GET
    fun search(@Query("s") query: String)


    companion object {

        const val BASE_URL = "https://www.omdbapi.com/?apikey=43a2d89b"
        var retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun create() =
            retrofit.create(MovieService::class.java)

    }
}
