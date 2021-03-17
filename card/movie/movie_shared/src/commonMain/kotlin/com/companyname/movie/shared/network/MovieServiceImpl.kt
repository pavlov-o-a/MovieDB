package com.companyname.movie.shared.network

import com.companyname.shared.KtorFactory
import com.companyname.shared.entities.Credits
import com.companyname.shared.entities.Movie
import com.companyname.shared.entities.RepositoryData
import com.companyname.shared.entities.RepositoryError
import com.companyname.shared.network.Mapper.toCredits
import com.companyname.shared.network.Mapper.toMovie
import com.companyname.shared.network.entities.CreditsResponse
import com.companyname.shared.network.entities.MovieResponse
import io.ktor.client.request.*

class MovieServiceImpl(private val apiKey: String) : MovieService {
    override suspend fun getMovie(id: Int): RepositoryData<Movie> {
        return try {
            val wrapper = KtorFactory.getKtor()
            val request = wrapper.request(apiKey, "movie/$id")
            RepositoryData(wrapper.ktorClient.get<MovieResponse>(request).toMovie())
        } catch (exc: Exception) {
            RepositoryData(null, RepositoryError.UNKNOWN.message(exc.message))
        }
    }

    override suspend fun getCredits(id: Int): RepositoryData<Credits> {
        return try {
            val wrapper = KtorFactory.getKtor()
            val request = wrapper.request(apiKey, "movie/$id/credits")
            RepositoryData(wrapper.ktorClient.get<CreditsResponse>(request).toCredits())
        } catch (exc: Exception) {
            RepositoryData(null, RepositoryError.UNKNOWN.message(exc.message))
        }
    }
}