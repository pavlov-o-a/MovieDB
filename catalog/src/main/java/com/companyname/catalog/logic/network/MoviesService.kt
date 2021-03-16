package com.companyname.catalog.logic.network

import com.companyname.shared.entities.BaseMovie
import com.companyname.shared.entities.Movie
import com.companyname.shared.entities.Page
import com.companyname.shared.entities.RepositoryData

interface MoviesService {
    suspend fun getMovies(page: Int): RepositoryData<Page<BaseMovie>>
}