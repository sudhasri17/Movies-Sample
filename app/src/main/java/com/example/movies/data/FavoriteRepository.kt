package com.example.movies.data

import com.example.movies.database.tables.MovieTileTable
import com.example.movies.database.tables.MovieTileTable_Table
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.raizlabs.android.dbflow.sql.language.SQLite.select
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor() {

    fun addAsFavoriteMovie(movieTileTable: MovieTileTable){
        movieTileTable.save()
    }

    fun fetchFavoriteMovies(loadDataCallback: LoadDataCallback<List<MovieTileTable>>){
        try {
            select()
                .from(MovieTileTable::class.java)
                .where (MovieTileTable_Table.isFav.eq(true))
                .async()
                .queryResultCallback{ _, tResult ->
                    tResult.toListClose()?.let { loadDataCallback.onDataLoaded(it) }
                }
                .execute();
        } catch (e: Exception) {
            loadDataCallback.onDataNotAvailable(e.message?:e.toString())
        }
    }
}