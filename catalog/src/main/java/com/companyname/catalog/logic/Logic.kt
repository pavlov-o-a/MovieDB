package com.companyname.catalog.logic

import com.companyname.shared.entities.*

interface Logic {
    suspend fun getMovies(page: Int, isSync: Boolean): RepositoryData<Page<BaseMovie>>
}