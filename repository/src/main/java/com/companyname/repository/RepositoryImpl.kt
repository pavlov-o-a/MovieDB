package com.companyname.repository

import com.companyname.repository.types.movie.MovieRepositoryImp
import com.companyname.repository.types.movies.MoviesRepositoryImpl

internal class RepositoryImpl: Repository {

    override fun getMoviesRepository() = MoviesRepositoryImpl()

    override fun getMovieRepository() = MovieRepositoryImp()
}