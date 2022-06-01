package com.example.movies.models


import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Search")
    val movieTiles: List<MovieTile>,
    val totalResults: String
) {
    data class MovieTile(
        val imdbID: String,
        @SerializedName("Poster")
        val poster: String,
        @SerializedName("Title")
        val title: String,
        @SerializedName("Year")
        val year: String
    )
}