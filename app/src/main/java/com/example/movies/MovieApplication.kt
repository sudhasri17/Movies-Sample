package com.example.movies

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager

class MovieApplication : Application() {

//    private applicationComponent: ApplicationComponent = DaggerApplicationComponent.create()

    override fun onCreate() {
        super.onCreate()
        FlowManager.init(FlowConfig.Builder(this).build())
        Fresco.initialize(this)
    }
}