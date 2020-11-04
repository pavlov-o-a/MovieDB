package com.companyname.moviedb

import android.app.Application
import com.companyname.moviedb.di.AppComponent
import com.companyname.moviedb.di.AppComponentProvider
import com.companyname.moviedb.di.DaggerAppComponent

class AppMovieDB: Application(), AppComponentProvider {
    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.create()
    }

    override fun provideAppComponent(): AppComponent = component
}