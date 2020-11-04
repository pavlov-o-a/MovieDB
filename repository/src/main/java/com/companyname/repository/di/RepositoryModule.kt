package com.companyname.repository.di

import com.companyname.repository.types.movie.MovieRepository
import com.companyname.repository.types.movie.MovieRepositoryImp
import com.companyname.repository.types.movies.MoviesRepository
import com.companyname.repository.types.movies.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun movie(): MovieRepository = MovieRepositoryImp()
    @Provides
    fun movies(): MoviesRepository = MoviesRepositoryImpl()
}