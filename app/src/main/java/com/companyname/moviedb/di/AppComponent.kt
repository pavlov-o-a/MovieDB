package com.companyname.moviedb.di

import com.companyname.repository.di.RepositoryModule
import com.companyname.repository.types.movie.MovieRepository
import com.companyname.repository.types.movies.MoviesRepository
import dagger.Component

@AppScope
@Component(modules = [RepositoryModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(): AppComponent
    }

    fun movieRep(): MovieRepository
    fun moviesRep(): MoviesRepository
}