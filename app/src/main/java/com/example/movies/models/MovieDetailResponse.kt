package com.example.movies.models

import com.example.movies.database.tables.MovieDetail
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.PrimaryKey

data class MovieDetailResponse(

    @SerializedName("Actors")
    val actors: String,
    @SerializedName("Awards")
    val awards: String,
    @SerializedName("Director")
    val director: String,
    @SerializedName("Genre")
    val genre: String,
    @PrimaryKey
    val imdbID: String,
    val imdbRating: String,
    @SerializedName("Language")
    val language: String,
    @SerializedName("Plot")
    val plot: String,
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("Rated")
    val rated: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
) {
    fun toMovieDetail(): MovieDetail {
        return MovieDetail().apply {
            actors = this@MovieDetailResponse.actors
            awards = this@MovieDetailResponse.awards
            director = this@MovieDetailResponse.director
            genre = this@MovieDetailResponse.genre
            imdbID = this@MovieDetailResponse.imdbID
            imdbRating = this@MovieDetailResponse.imdbRating
            language = this@MovieDetailResponse.language
            plot = this@MovieDetailResponse.plot
            poster = this@MovieDetailResponse.poster
            rated = this@MovieDetailResponse.rated
            title = this@MovieDetailResponse.title
            year = this@MovieDetailResponse.year
        }
    }
}
