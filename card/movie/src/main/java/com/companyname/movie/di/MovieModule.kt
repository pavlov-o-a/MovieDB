package com.companyname.movie.di

import com.companyname.movie.logic.Logic
import com.companyname.movie.logic.LogicImpl
import com.companyname.movie.logic.network.MovieAPI
import com.companyname.movie.logic.network.MovieService
import com.companyname.movie.logic.network.MovieServiceImpl
import com.companyname.movie.logic.storage.MovieStorage
import com.companyname.movie.logic.storage.MovieStorageImpl
import com.companyname.repository.net.ServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MovieModule {

    @Binds
    abstract fun logic(logic: LogicImpl): Logic

    @Binds
    abstract fun movieService(movieService: MovieServiceImpl): MovieService

    @Binds
    abstract fun movieStorage(movieStorage: MovieStorageImpl): MovieStorage

    companion object {
        @Provides
        fun movieApi(serviceFactory: ServiceFactory): MovieAPI {
            return serviceFactory.getService(MovieAPI::class.java)
        }
    }
}