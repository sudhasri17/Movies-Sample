package com.example.movies.di

import com.example.movies.MainActivity
import com.example.movies.ui.fav.FavoriteFragment
import dagger.Component
import dagger.Subcomponent

@Subcomponent
interface ActivityComponent{

    // Factory that is used to create instances of this subcomponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(favoriteFragment: FavoriteFragment)
}