package com.companyname.movie.logic.network

import com.companyname.shared.entities.Credits
import com.companyname.shared.entities.Movie
import com.companyname.shared.entities.RepositoryData

interface MovieService {
    suspend fun getMovie(id: Int): RepositoryData<Movie>
    suspend fun getCredits(id: Int): RepositoryData<Credits>
}