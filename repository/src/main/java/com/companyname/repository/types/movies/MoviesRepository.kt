package com.companyname.repository.types.movies

import com.companyname.common.entities.MoviesPage
import com.companyname.common.entities.RepositoryData

interface MoviesRepository {
    suspend fun getMovies(page: Int): RepositoryData<MoviesPage>
}