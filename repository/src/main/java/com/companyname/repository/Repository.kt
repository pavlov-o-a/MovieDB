package com.companyname.repository

import com.companyname.repository.types.movie.MovieRepository
import com.companyname.repository.types.movies.MoviesRepository

interface Repository {
    fun getMoviesRepository(): MoviesRepository
    fun getMovieRepository(): MovieRepository
}