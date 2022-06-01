package com.example.movies.data

import java.lang.Exception

interface FavoriteResponseListener {

    fun <T> onResponse(result: Result<T>)
    fun onError(exception: Exception)
}

interface LoadDataCallback<T> {

    fun onDataLoaded(data: T)

    fun onDataNotAvailable(error: String = "Data not available")
}


sealed class Result<out R>() {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is com.example.movies.data.Result.Success && data != null