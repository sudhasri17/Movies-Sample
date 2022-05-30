package com.example.movies.database.tables

import com.example.movies.database.MoviesDB
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(allFields = true, database = MoviesDB::class)
class SearchQueries: BaseModel(){
    @PrimaryKey
    lateinit var queryString: String
    = var lastUsedTime: Long = 0L
}