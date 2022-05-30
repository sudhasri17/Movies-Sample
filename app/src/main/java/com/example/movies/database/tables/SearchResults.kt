package com.example.movies.database.tables

import com.example.movies.database.MoviesDB
import com.example.movies.models.SearchResult
import com.raizlabs.android.dbflow.annotation.ForeignKey
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.annotation.Unique
import com.raizlabs.android.dbflow.structure.BaseModel

/*@Table(database = MoviesDB::class, allFields = true)
abstract class SearchResultsTable() : BaseModel(){
    @PrimaryKey @Unique
    lateinit var queryString: String
    @ForeignKey
    lateinit var imdbID: String
}*/


@Table(database = MoviesDB::class, allFields = true)
class MovieTileTable : BaseModel() {
    @PrimaryKey
    lateinit var imdbID: String
    lateinit var poster: String
    lateinit var title: String
    var isFav: Boolean = false
    lateinit var year: String
}