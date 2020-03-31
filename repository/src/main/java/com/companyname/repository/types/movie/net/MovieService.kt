package com.companyname.repository.types.movie.net

import android.util.Log
import com.companyname.repository.net.RetrofitFactory
import com.companyname.repository.net.entities.CreditsResponse
import com.companyname.repository.net.entities.MovieResponse

internal class MovieService {

    private fun getApi() = RetrofitFactory.getService(MovieAPI::class.java)

    suspend fun getMovie(id: Int): MovieResponse?{
        try {
            return getApi().getMovie(id)
        } catch (exc: Exception){
            Log.e("movies", "smth went wrong", exc)
        }
        return null
    }

    suspend fun getCredits(movieId: Int): CreditsResponse?{
        try {
            return getApi().getCredits(movieId)
        } catch (exc: Exception){
            Log.e("movies", "smth went wrong", exc)
        }
        return null
    }
}