package com.companyname.repository.types.movie.net

import com.companyname.repository.net.RetrofitFactory
import com.companyname.repository.net.entities.CreditsResponse
import com.companyname.repository.net.entities.MovieResponse
import com.companyname.repository.net.entities.NetResponse

internal class MovieService {

    private fun getApi() = RetrofitFactory.getService(MovieAPI::class.java)

    suspend fun getMovie(id: Int): NetResponse<MovieResponse?>{
        return try {
            NetResponse(getApi().getMovie(id))
        } catch (exc: Exception){
            NetResponse(null, exc.message)
        }
    }

    suspend fun getCredits(movieId: Int): NetResponse<CreditsResponse?>{
        return try {
            NetResponse(getApi().getCredits(movieId))
        } catch (exc: Exception){
            NetResponse(null, exc.message)
        }
    }
}