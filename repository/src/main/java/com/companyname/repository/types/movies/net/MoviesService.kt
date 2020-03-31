package com.companyname.repository.types.movies.net

import android.util.Log
import com.companyname.repository.net.RetrofitFactory
import com.companyname.repository.net.entities.MoviesTop

internal class MoviesService {

    private fun getApi() = RetrofitFactory.getService(MoviesApi::class.java)

    suspend fun getMovies(page: Int): MoviesTop? {
        try {
            return getApi().getMovies(page)
        } catch (exc: Exception){
            Log.e("movies", "smth went wrong", exc)
        }
        return null
    }

}