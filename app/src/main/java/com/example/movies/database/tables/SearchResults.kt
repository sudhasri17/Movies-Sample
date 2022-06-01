package com.example.movies.database.tables

import com.example.movies.database.MoviesDB
import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(allFields = true, database = MoviesDB::class)
class SearchQuery: BaseModel(){
    @PrimaryKey
    @Unique
    lateinit var queryString: String
    var lastUsedTime: Long = 0L
}


@Table(database = MoviesDB::class, allFields = true)
class MovieTileTable : BaseModel() {
    @PrimaryKey
    @Unique
    lateinit var imdbID: String
    lateinit var poster: String
    lateinit var title: String
    var isFav: Boolean = false
    lateinit var year: String
}

@Table(database = MoviesDB::class)
class SearchMovieTileTable:BaseModel(){
    @PrimaryKey(autoincrement = true)
    var _id: Long = 0

    @ForeignKey(saveForeignKeyModel = false)
    var movieTileTable: MovieTileTable? = null

    @ForeignKey(saveForeignKeyModel = false)
    var queryString: SearchQuery? = null
}