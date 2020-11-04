package com.companyname.moviedb.di

import android.content.Context

interface AppComponentProvider {
    fun provideAppComponent(): AppComponent
}

fun Context.getAppComponent(): AppComponent {
    if (applicationContext is AppComponentProvider)
        return (applicationContext as AppComponentProvider).provideAppComponent()
    else
        throw RuntimeException("Context is not AppComponentProvider")
}