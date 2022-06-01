package com.example.movies.di.components

import com.example.movies.MainActivity
import com.example.movies.di.ActivityScope
import com.example.movies.ui.fav.FavoriteFragment
import com.example.movies.ui.fav.FavoriteViewModel
import dagger.Subcomponent

@Subcomponent
@ActivityScope
interface ActivityComponent{

    // Factory that is used to create instances of this subcomponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }


    fun inject(mainActivity: MainActivity)
    fun inject(favoriteFragment: FavoriteFragment)
    fun inject(favoriteViewModel: FavoriteViewModel)
}