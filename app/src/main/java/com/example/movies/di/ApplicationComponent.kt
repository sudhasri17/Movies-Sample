package com.example.movies.di

import android.app.Application
import com.example.movies.api.MovieService
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component (modules = [NetworkModule::class, ActivityModule::class, AndroidInjectionModule::class])
interface ApplicationComponent {

    fun inject(application: Application)

    @Component.Builder
    interface Builder {

        fun build(): ApplicationComponent

        @BindsInstance
        fun applicationBind(application: Application): Builder

    }
    @Singleton
    fun provideMovieService() : MovieService
}