package com.companyname.catalog.logic

import com.companyname.catalog.di.CatalogScope
import com.companyname.common.entities.MoviesPage
import com.companyname.common.entities.RepositoryData
import com.companyname.repository.types.movies.MoviesRepository
import javax.inject.Inject

@CatalogScope
class LogicImpl @Inject constructor(private val moviesRepository: MoviesRepository): Logic {

    override suspend fun getMovies(page: Int): RepositoryData<MoviesPage> {
        return moviesRepository.getMovies(page)
    }
}