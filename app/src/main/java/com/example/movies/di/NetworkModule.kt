package com.example.movies.di

import com.example.movies.api.MovieService
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun provideMovieService() = MovieService.create()

}