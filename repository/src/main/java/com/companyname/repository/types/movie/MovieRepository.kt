package com.companyname.repository.types.movie

import com.companyname.common.entities.Credits
import com.companyname.common.entities.Movie
import com.companyname.common.entities.RepositoryData

interface MovieRepository {

    suspend fun getMovie(id: Int): RepositoryData<Movie>

    suspend fun getCredits(movieId: Int): RepositoryData<Credits>
}