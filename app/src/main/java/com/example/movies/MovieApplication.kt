package com.example.movies

import android.app.Application
import com.example.movies.api.GlobalResponseOperator
import com.example.movies.di.components.ApplicationComponent
import com.example.movies.di.components.DaggerApplicationComponent
import com.facebook.drawee.backends.pipeline.Fresco
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import com.skydoves.sandwich.SandwichInitializer
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MovieApplication() : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        FlowManager.init(FlowConfig.Builder(this).build())
        Fresco.initialize(this)
        SandwichInitializer.sandwichOperator = GlobalResponseOperator<Any>(this)

    }

    override fun applicationInjector() = DaggerApplicationComponent
        .builder()
        .application(this)
        .build()
}