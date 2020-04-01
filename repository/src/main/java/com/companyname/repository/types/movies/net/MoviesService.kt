package com.companyname.repository.types.movies.net

import com.companyname.repository.net.RetrofitFactory
import com.companyname.repository.net.entities.MoviesTop
import com.companyname.repository.net.entities.NetResponse

internal class MoviesService {

    private fun getApi() = RetrofitFactory.getService(MoviesApi::class.java)

    suspend fun getMovies(page: Int): NetResponse<MoviesTop?> {
        return try {
            NetResponse(getApi().getMovies(page))
        } catch (exc: Exception){
            NetResponse(null, exc.message)
        }
    }

}