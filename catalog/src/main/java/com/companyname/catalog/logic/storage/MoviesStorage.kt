package com.companyname.catalog.logic.storage

import com.companyname.shared.entities.BaseMovie
import com.companyname.shared.entities.Page
import com.companyname.shared.entities.RepositoryData

interface MoviesStorage {
    fun getMovies(page: Int): RepositoryData<Page<BaseMovie>>
}