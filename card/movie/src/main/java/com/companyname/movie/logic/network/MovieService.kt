package com.companyname.movie.logic.network

import com.companyname.common.entities.Credits
import com.companyname.common.entities.Movie
import com.companyname.common.entities.RepositoryData

interface MovieService {
    suspend fun getMovie(id: Int): RepositoryData<Movie>
    suspend fun getCredits(id: Int): RepositoryData<Credits>
}