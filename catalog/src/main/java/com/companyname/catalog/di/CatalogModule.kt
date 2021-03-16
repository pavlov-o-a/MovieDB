package com.companyname.catalog.di

import com.companyname.catalog.logic.Logic
import com.companyname.catalog.logic.LogicImpl
import com.companyname.catalog.logic.network.MoviesAPI
import com.companyname.catalog.logic.network.MoviesService
import com.companyname.catalog.logic.network.MoviesServiceImpl
import com.companyname.catalog.logic.storage.MoviesStorage
import com.companyname.catalog.logic.storage.MoviesStorageImpl
import com.companyname.repository.net.RetrofitFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class CatalogModule {

    @Binds
    abstract fun logic(logicImpl: LogicImpl): Logic

    @Binds
    abstract fun moviesStorage(storage: MoviesStorageImpl): MoviesStorage

    @Binds
    abstract fun moviesService(service: MoviesServiceImpl): MoviesService

    companion object {
        @Provides
        fun moviesApi(): MoviesAPI {
            return RetrofitFactory.getService(MoviesAPI::class.java)
        }
    }
}