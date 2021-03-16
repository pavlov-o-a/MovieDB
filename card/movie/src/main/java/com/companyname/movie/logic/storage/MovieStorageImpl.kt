package com.companyname.movie.logic.storage

import com.companyname.common.entities.Movie
import com.companyname.common.entities.RepositoryData
import javax.inject.Inject

class MovieStorageImpl @Inject constructor() : MovieStorage {
    override suspend fun getMovie(id: Int): RepositoryData<Movie> {
        return RepositoryData(null)
    }
}