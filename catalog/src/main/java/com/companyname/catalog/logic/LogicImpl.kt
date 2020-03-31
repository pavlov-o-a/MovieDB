package com.companyname.catalog.logic

import com.companyname.common.entities.MoviesPage
import com.companyname.common.entities.RepositoryData
import com.companyname.repository.RepositoryFactory


class LogicImpl: Logic {

    override suspend fun getMovies(page: Int): RepositoryData<MoviesPage> {
        return RepositoryFactory.getRepository().getMoviesRepository().getMovies(page)
    }
}