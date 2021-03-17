package com.companyname.movie.logic.network

import com.companyname.shared.entities.Credits
import com.companyname.shared.entities.Movie
import com.companyname.shared.entities.RepositoryData
import com.companyname.shared.entities.RepositoryError
import com.companyname.repository.net.Mapper.toCredits
import com.companyname.repository.net.Mapper.toMovie
import javax.inject.Inject

class MovieServiceImpl @Inject constructor(private val api: MovieAPI) : MovieService {
    override suspend fun getMovie(id: Int): RepositoryData<Movie> {
        return try {
            RepositoryData(api.getMovie(id).run { toMovie() })
        } catch (exc: Exception) {
            RepositoryData(null, RepositoryError.UNKNOWN.message(exc.message))
        }
    }

    override suspend fun getCredits(id: Int): RepositoryData<Credits> {
        return try {
            RepositoryData(api.getCredits(id).run { toCredits() })
        } catch (exc: Exception) {
            RepositoryData(null, RepositoryError.UNKNOWN.message(exc.message))
        }
    }
}