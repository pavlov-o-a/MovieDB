package com.companyname.moviedb.di

import dagger.Component

@AppScope
@Component(modules = [])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(): AppComponent
    }
}