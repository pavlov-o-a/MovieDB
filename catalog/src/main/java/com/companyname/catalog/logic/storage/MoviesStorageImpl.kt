package com.companyname.catalog.logic.storage

import com.companyname.common.entities.BaseMovie
import com.companyname.common.entities.Page
import com.companyname.common.entities.RepositoryData
import javax.inject.Inject

class MoviesStorageImpl @Inject constructor() : MoviesStorage {
    override fun getMovies(page: Int): RepositoryData<Page<BaseMovie>> {
        return RepositoryData(Page(page, 0, listOf()))
    }
}