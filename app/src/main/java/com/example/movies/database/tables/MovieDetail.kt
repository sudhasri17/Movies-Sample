package com.example.movies.database.tables

import com.example.movies.database.MoviesDB
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = MoviesDB::class, allFields = true)
class MovieDetail : BaseModel() {

    lateinit var actors: String
    lateinit var awards: String
    lateinit var director: String
    lateinit var genre: String
    @PrimaryKey
    lateinit var imdbID: String
    lateinit var imdbRating: String
    lateinit var language: String
    lateinit var plot: String
    lateinit var poster: String
    lateinit var rated: String
    lateinit var title: String
    lateinit var year: String
}