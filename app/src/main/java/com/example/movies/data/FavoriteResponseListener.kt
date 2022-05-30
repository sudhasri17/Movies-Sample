package com.example.movies.data

import com.example.movies.database.tables.MovieTileTable
import com.example.movies.models.SearchResult
import java.lang.Exception

interface FavoriteResponseListener {

    fun onResponse(favorites: List<MovieTileTable>)
}