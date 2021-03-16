package com.companyname.movie.logic

import com.companyname.common.entities.Credits
import com.companyname.common.entities.Movie
import com.companyname.common.entities.RepositoryData
import com.companyname.movie.di.MovieScope
import com.companyname.movie.logic.network.MovieService
import com.companyname.movie.logic.storage.MovieStorage
import javax.inject.Inject

@MovieScope
class LogicImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieStorage: MovieStorage
) : Logic {
    override suspend fun getMovie(movieId: Int): RepositoryData<Movie> {
        val movie = movieService.getMovie(movieId)
        if (movie.error == null) return movie
        return movieStorage.getMovie(movieId)
    }

    override suspend fun getCredits(movieId: Int): RepositoryData<Credits> {
        return movieService.getCredits(movieId)
    }
}