package com.companyname.catalog.logic

import com.companyname.catalog.di.CatalogScope
import com.companyname.catalog.logic.network.MoviesService
import com.companyname.catalog.logic.storage.MoviesStorage
import com.companyname.shared.entities.*
import javax.inject.Inject

@CatalogScope
class LogicImpl @Inject constructor(
    private val moviesService: MoviesService,
    private val moviesStorage: MoviesStorage
) : Logic {

    override suspend fun getMovies(page: Int, isSync: Boolean): RepositoryData<Page<BaseMovie>> {
        if (isSync) {
            val movies = moviesService.getMovies(page)
            return if (movies.error != null) {
                moviesStorage.getMovies(page).run { RepositoryData(data, movies.error) }
            } else movies
        }
        return moviesStorage.getMovies(page)
    }
}