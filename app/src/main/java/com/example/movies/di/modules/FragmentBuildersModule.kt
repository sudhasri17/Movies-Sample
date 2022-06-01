package com.example.movies.di.modules

import com.example.movies.ui.fav.FavoriteFragment
import com.example.movies.ui.movieDetail.MovieDetailFragment
import com.example.movies.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun favoriteFragment(): FavoriteFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun movieDetailFragment(): MovieDetailFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun searchFragment(): SearchFragment

}