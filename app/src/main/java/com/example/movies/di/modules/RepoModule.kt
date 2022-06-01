package com.example.movies.di.modules

import com.example.movies.api.MovieService
import com.example.movies.data.*
import com.example.movies.di.LocalDataSource
import com.example.movies.di.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    fun provideFavoriteRepository(): FavoriteRepository = FavoriteRepository()

    @Provides
    @Singleton
    @LocalDataSource
    fun provideLocalMovieDetailSource() =  LocalMovieDetailDataSource()

    @Provides
    @Singleton
    @RemoteDataSource
    fun provideRemoteMovieDetailSource(movieService: MovieService) = RemoteMovieDetailDataSource(movieService)

    @Provides
    @Singleton
    fun provideMovieDetailRepository(
        @LocalDataSource localMovieDetailDataSource: LocalMovieDetailDataSource,
        @RemoteDataSource remoteMovieDetailDataSource: RemoteMovieDetailDataSource
    ): MovieDetailRepository = MovieDetailRepository(localMovieDetailDataSource, remoteMovieDetailDataSource)

    @Provides
    @Singleton
    fun provideLocalSearchDataSource() = LocalSearchDataSource()

    @Provides
    @Singleton
    fun provideRemoteSearchDataSource(movieService: MovieService) = RemoteSearchDataSource(movieService)

    @Provides
    @Singleton
    fun provideSearchRepo(
         localSearchDataSource: LocalSearchDataSource,
         remoteSearchDataSource: RemoteSearchDataSource
    ): SearchRepository = SearchRepository(localSearchDataSource, remoteSearchDataSource)
}