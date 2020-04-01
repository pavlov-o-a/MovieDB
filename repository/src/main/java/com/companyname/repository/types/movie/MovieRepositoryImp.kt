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
        val response = MovieService().getMovie(id)
        val movie = response.data?.toMovie()
        return if (movie != null){
            RepositoryData(movie)
        } else {
            RepositoryData<Movie>(
                null,
                RepositoryErrors.UNKNOWN.message(response.error)
            )
        }
    }

    override suspend fun getCredits(movieId: Int): RepositoryData<Credits> {
        val response = MovieService().getCredits(movieId)
        val credits = response.data?.toCredits()
        return if (credits != null){
            RepositoryData(credits)
        } else {
            RepositoryData<Credits>(
                null,
                RepositoryErrors.UNKNOWN.message(response.error)
            )
        }
    }
}