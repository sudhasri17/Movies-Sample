package com.example.movies.data

import android.util.Log
import com.example.movies.api.MovieService
import com.example.movies.database.MoviesDB
import com.example.movies.database.tables.*
import com.example.movies.models.SearchResult
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.sql.language.ConditionGroup
import com.raizlabs.android.dbflow.sql.language.Join
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import com.skydoves.sandwich.request
import javax.inject.Inject


class LocalSearchDataSource {
    val db = FlowManager.getDatabase(MoviesDB::class.java)
    fun getSearchResults(
        query: String,
        loadDataCallback: LoadDataCallback<List<MovieTileTable>>
    ) {
        Log.d("SEARCH","localDataSource, queried")
        SQLite.select()
            .from(MovieTileTable::class.java)
            .join(SearchMovieTileTable::class.java, Join.JoinType.INNER)
            .on(ConditionGroup.clause()
                .and(SearchMovieTileTable_Table.queryString_queryString.eq(query))
                .and(MovieTileTable_Table.imdbID.eq(SearchMovieTileTable_Table.movieTileTable_imdbID)))
            .async()
            .queryResultCallback{ _, tResult ->
                tResult.toListClose()?.let { result ->
                    if(result.isEmpty()){
                        Log.d("SEARCH","localDataSource, data empty")
                        loadDataCallback.onDataNotAvailable()
                    } else{
                        Log.d("SEARCH","localDataSource, data loaded")
                        loadDataCallback.onDataLoaded(result)
                    }
                }
            }
            .execute()
        /*SQLite.select()
            .from(MovieTileTable::class.java)
            .async()
            .queryResultCallback{ _, tResult ->
                tResult.toListClose()?.let { result ->
                    if(result.isEmpty()){
                        loadDataCallback.onDataNotAvailable()
                    } else{
                        loadDataCallback.onDataLoaded(result)
                    }
                }
            }
            .execute()*/
    }

    fun saveSearchResults(query: String, results: List<MovieTileTable>) {
        //save query
        val searchQuery = SearchQuery().apply {
            queryString = query
            lastUsedTime = System.currentTimeMillis()
            save()
        }

        //save movieTiles
        val movieListTransaction = FastStoreModelTransaction
            .saveBuilder(FlowManager.getModelAdapter(MovieTileTable::class.java))
                .addAll(results)
                .build()
        db.beginTransactionAsync(movieListTransaction).build().execute()


        //save searchQuery_MovieTile relation
        val relation = results.map {
            SearchMovieTileTable().apply {
                queryString = searchQuery
                movieTileTable = it
            }
        }

        val relationListTransaction = FastStoreModelTransaction
            .saveBuilder(FlowManager.getModelAdapter(SearchMovieTileTable::class.java))
            .addAll(relation)
            .build()
        db.beginTransactionAsync(relationListTransaction).build().execute()
    }

    fun geRecentSearches(query: String, loadDataCallback: LoadDataCallback<List<String>>) {
        try {
            SQLite.select(SearchQuery_Table.queryString)
                .from(SearchQuery::class.java)
                .where(SearchQuery_Table.queryString.like("%$query%"))
                .async()
                .queryResultCallback{ _, tResult ->
                    val result = tResult.toListClose()?.map { it.queryString } ?: emptyList()
                    loadDataCallback.onDataLoaded(result)
                }
                .execute();
        } catch (e: Exception) {
            loadDataCallback.onDataNotAvailable(e.message?:e.toString())
        }
    }
}

class RemoteSearchDataSource @Inject constructor(val movieService: MovieService)  {
    fun getSearchResults(
        query: String,
        loadDataCallback: LoadDataCallback<List<MovieTileTable>>
    ) {
        Log.d("SEARCH","RemoteDataSource queried")
        movieService.search( query).request {
            response: ApiResponse<SearchResult> ->
            when (response) {
                is ApiResponse.Success -> {
                    var movieTileTable: MovieTileTable
                    val list = response.data.movieTiles.map {
                        movieTileTable = MovieTileTable( )
                        movieTileTable.poster = it.poster
                        movieTileTable.year = it.year
                        movieTileTable.imdbID = it.imdbID
                        movieTileTable.title = it.title
                        movieTileTable
                    }
                    loadDataCallback.onDataLoaded(list)
                    Log.d("SEARCH","RemoteDataSource, data loaded")
                }
                is ApiResponse.Failure.Error -> {
                    loadDataCallback.onDataNotAvailable(
                        response.message()
                    )
                }
                is ApiResponse.Failure.Exception -> {
                    loadDataCallback.onDataNotAvailable(
                        response.message()
                    )
                }
                }
            }
        }
}