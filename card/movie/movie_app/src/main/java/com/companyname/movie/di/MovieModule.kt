package com.companyname.movie.di

import com.companyname.movie.logic.Logic
import com.companyname.movie.logic.LogicImpl
import com.companyname.movie.logic.storage.MovieStorage
import com.companyname.movie.logic.storage.MovieStorageImpl
import com.companyname.movie.shared.network.MovieService
import com.companyname.movie.shared.network.MovieServiceImpl
import com.companyname.moviedb.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MovieModule {

    @Binds
    abstract fun logic(logic: LogicImpl): Logic

    @Binds
    abstract fun movieStorage(movieStorage: MovieStorageImpl): MovieStorage

    companion object {

        @Provides
        fun movieService(): MovieService {
            return MovieServiceImpl(BuildConfig.API_KEY)
        }
    }
}