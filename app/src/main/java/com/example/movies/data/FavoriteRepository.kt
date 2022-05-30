package com.example.movies.data

import com.example.movies.database.tables.MovieTileTable
import com.example.movies.database.tables.MovieTileTable_Table
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.raizlabs.android.dbflow.sql.language.SQLite.select
import javax.inject.Inject


class FavoriteRepository @Inject constructor() {

    fun addAsFavoriteMovie(movieTileTable: MovieTileTable){
        movieTileTable.save()
    }

    fun fetchFavoriteMovies(favoriteResponseListener: FavoriteResponseListener){
        select()
            .from(MovieTileTable::class.java)
            .where (MovieTileTable_Table.isFav.eq(true))
            .async()
            .queryResultCallback{ _, tResult ->
                tResult.toListClose()?.let { favoriteResponseListener.onResponse(it) }
            }
            .execute();
    }
}