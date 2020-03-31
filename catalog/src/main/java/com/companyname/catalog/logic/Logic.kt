package com.companyname.catalog.logic

import com.companyname.common.entities.MoviesPage
import com.companyname.common.entities.RepositoryData

interface Logic {
    suspend fun getMovies(page: Int): RepositoryData<MoviesPage>
}