package com.example.movies.di.modules

import android.app.Application
import android.content.Context
import com.example.movies.api.MovieService
import com.example.movies.data.FavoriteRepository
import com.example.movies.data.LocalMovieDetailDataSource
import com.example.movies.data.MovieDetailDataSource
import com.example.movies.data.MovieDetailRepository
import com.example.movies.di.LocalDataSource
import com.example.movies.di.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    ViewModelModule::class
])
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context
/*
    @Binds
    @Singleton
    @LocalDataSource
    abstract fun bindLocalMovieDetailRepo(localMovieDetailDataSource: LocalMovieDetailDataSource):MovieDetailDataSource

    @Binds
    @Singleton
    @RemoteDataSource
    abstract fun bindRemoteMovieDetailRepo(remoMovieDetailDataSource: MovieDetailDataSource):MovieDetailDataSource*/

}