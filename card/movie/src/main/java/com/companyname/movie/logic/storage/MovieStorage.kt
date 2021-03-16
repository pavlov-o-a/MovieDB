package com.companyname.movie.logic.storage

import com.companyname.common.entities.Movie
import com.companyname.common.entities.RepositoryData

interface MovieStorage {
    suspend fun getMovie(id: Int): RepositoryData<Movie>
}