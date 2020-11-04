package com.companyname.movie.logic

import com.companyname.common.entities.Credits
import com.companyname.common.entities.Movie
import com.companyname.common.entities.RepositoryData
import com.companyname.movie.di.MovieScope
import com.companyname.repository.types.movie.MovieRepository
import javax.inject.Inject

@MovieScope
class LogicImpl @Inject constructor(private val movieRepository: MovieRepository): Logic {
    override suspend fun getMovie(id: Int): RepositoryData<Movie> {
        return movieRepository.getMovie(id)
    }

    override suspend fun getCredits(movieId: Int): RepositoryData<Credits> {
        return movieRepository.getCredits(movieId)
    }
}