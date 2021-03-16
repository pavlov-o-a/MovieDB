package com.companyname.catalog.logic.network

import com.companyname.common.entities.*
import com.companyname.repository.net.Mapper.toMoviesPage
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