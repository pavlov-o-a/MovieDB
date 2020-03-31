package com.companyname.repository.types.movie

import com.companyname.common.entities.Credits
import com.companyname.common.entities.Movie
import com.companyname.common.entities.RepositoryData
import com.companyname.common.entities.RepositoryErrors
import com.companyname.repository.types.movie.net.MovieService
import com.companyname.repository.net.Mapper.toMovie
import com.companyname.repository.net.Mapper.toCredits

internal class MovieRepositoryImp(): MovieRepository{
    override suspend fun getMovie(id: Int): RepositoryData<Movie> {
        val fromNet = MovieService().getMovie(id)?.toMovie()
        return if (fromNet != null){
            RepositoryData(fromNet)
        } else {
            RepositoryData<Movie>(
                null,
                RepositoryErrors.UNKNOWN
            )
        }
    }

    override suspend fun getCredits(movieId: Int): RepositoryData<Credits> {
        val fromNet = MovieService().getCredits(movieId)?.toCredits()
        return if (fromNet != null){
            RepositoryData(fromNet)
        } else {
            RepositoryData<Credits>(
                null,
                RepositoryErrors.UNKNOWN
            )
        }
    }
}