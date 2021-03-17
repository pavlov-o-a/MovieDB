package com.companyname.movie.logic.storage

import com.companyname.shared.entities.Movie
import com.companyname.shared.entities.RepositoryData

interface MovieStorage {
    suspend fun getMovie(id: Int): RepositoryData<Movie>
}