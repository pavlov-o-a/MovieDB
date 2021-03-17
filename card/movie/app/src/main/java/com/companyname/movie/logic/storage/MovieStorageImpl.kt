package com.companyname.movie.logic.storage

import com.companyname.shared.entities.Movie
import com.companyname.shared.entities.RepositoryData
import javax.inject.Inject

class MovieStorageImpl @Inject constructor() : MovieStorage {
    override suspend fun getMovie(id: Int): RepositoryData<Movie> {
        return RepositoryData(null)
    }
}