package com.companyname.movie.logic

import com.companyname.common.entities.Credits
import com.companyname.common.entities.Movie
import com.companyname.common.entities.RepositoryData
import com.companyname.repository.RepositoryFactory

class LogicImpl: Logic {
    override suspend fun getMovie(id: Int): RepositoryData<Movie> {
        return RepositoryFactory.getRepository().getMovieRepository().getMovie(id)
    }

    override suspend fun getCredits(movieId: Int): RepositoryData<Credits> {
        return RepositoryFactory.getRepository().getMovieRepository().getCredits(movieId)
    }
}