package com.companyname.movie.logic

import com.companyname.common.entities.Credits
import com.companyname.common.entities.Movie
import com.companyname.common.entities.RepositoryData

interface Logic {

    suspend fun getMovie(movieId: Int): RepositoryData<Movie>

    suspend fun getCredits(movieId: Int): RepositoryData<Credits>
}