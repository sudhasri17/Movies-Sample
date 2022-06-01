package com.example.movies.di.modules

import com.example.movies.MainActivity
import com.example.movies.di.ActivityScope
import com.example.movies.di.components.ActivityComponent
import com.example.movies.di.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(
        modules = [
            FragmentBuildersModule::class
        ]
    )
    abstract fun mainActivity(): MainActivity
}