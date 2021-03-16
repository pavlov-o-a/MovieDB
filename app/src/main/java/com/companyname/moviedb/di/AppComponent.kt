package com.companyname.moviedb.di

import com.companyname.common.AppScope
import com.companyname.repository.net.NetworkModule
import com.companyname.repository.net.RetrofitWrapper
import dagger.Component

@AppScope
@Component(modules = [NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }

    fun retrofitWrapper(): RetrofitWrapper
}