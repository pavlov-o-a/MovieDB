package com.companyname.movie.logic

import com.companyname.shared.entities.Credits
import com.companyname.shared.entities.Movie
import com.companyname.shared.entities.RepositoryData

interface Logic {

    suspend fun getMovie(movieId: Int): RepositoryData<Movie>

    suspend fun getCredits(movieId: Int): RepositoryData<Credits>
}