package com.example.movies.di.components

import android.app.Application
import com.example.movies.MovieApplication
import com.example.movies.api.MovieService
import com.example.movies.data.FavoriteRepository
import com.example.movies.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component (modules = [AppModule::class,
    ActivityModule::class,
    NetworkModule::class,
    RepoModule::class,
    AndroidSupportInjectionModule::class ])
interface ApplicationComponent : AndroidInjector<MovieApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MovieApplication): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(application: MovieApplication)
    fun FavoriteRepository(): FavoriteRepository
}