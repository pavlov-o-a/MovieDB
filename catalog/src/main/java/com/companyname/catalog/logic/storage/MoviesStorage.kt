package com.companyname.catalog.logic.storage

import com.companyname.common.entities.BaseMovie
import com.companyname.common.entities.Movie
import com.companyname.common.entities.Page
import com.companyname.common.entities.RepositoryData

interface MoviesStorage {
    fun getMovies(page: Int): RepositoryData<Page<BaseMovie>>
}