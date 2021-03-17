package com.companyname.catalog.logic.network

import com.companyname.shared.entities.BaseMovie
import com.companyname.shared.entities.Page
import com.companyname.shared.entities.RepositoryData
import com.companyname.shared.entities.RepositoryError
import com.companyname.shared.network.Mapper.toMoviesPage
import javax.inject.Inject

class MoviesServiceImpl @Inject constructor(private val api: MoviesAPI) : MoviesService {
    override suspend fun getMovies(page: Int): RepositoryData<Page<BaseMovie>> {
        return try {
            api.getMovies(page).run {
                RepositoryData(toMoviesPage())
            }
        } catch (exc: Exception) {
            RepositoryData(null, RepositoryError.UNKNOWN.message(exc.message))
        }
    }
}