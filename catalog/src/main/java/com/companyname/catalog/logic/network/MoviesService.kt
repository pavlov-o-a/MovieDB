package com.companyname.catalog.logic.network

import com.companyname.common.entities.BaseMovie
import com.companyname.common.entities.Movie
import com.companyname.common.entities.Page
import com.companyname.common.entities.RepositoryData

interface MoviesService {
    suspend fun getMovies(page: Int): RepositoryData<Page<BaseMovie>>
}