package com.companyname.repository.types.movies.net

import com.companyname.repository.net.entities.MoviesTop
import retrofit2.http.GET
import retrofit2.http.Query


internal interface MoviesApi{

    @GET("discover/movie")
    suspend fun getMovies(@Query("page") page: Int): MoviesTop
}