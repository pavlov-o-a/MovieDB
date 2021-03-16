package com.companyname.movie.logic.network

import com.companyname.repository.net.entities.CreditsResponse
import com.companyname.repository.net.entities.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieAPI {

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Int): MovieResponse

    @GET("movie/{id}/credits")
    suspend fun getCredits(@Path("id") id: Int): CreditsResponse
}