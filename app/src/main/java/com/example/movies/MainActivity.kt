package com.example.movies

import android.os.Bundle
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL)
    }



}
