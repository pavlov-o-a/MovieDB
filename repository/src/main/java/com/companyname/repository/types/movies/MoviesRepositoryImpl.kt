package com.companyname.repository.types.movies

import com.companyname.common.entities.RepositoryData
import com.companyname.common.entities.RepositoryErrors
import com.companyname.repository.types.movies.net.MoviesService
import com.companyname.repository.types.movies.storage.MoviesStorage
import com.companyname.repository.net.Mapper.toMoviesPage
import com.companyname.common.entities.MoviesPage


internal class MoviesRepositoryImpl: MoviesRepository {
    override suspend fun getMovies(page: Int): RepositoryData<MoviesPage> {
        val response = MoviesService().getMovies(page)
        val moviesPage = response.data?.toMoviesPage()
        return if (moviesPage != null && moviesPage.movies.isNotEmpty()){
            MoviesStorage().saveMoviesPage(moviesPage)
            RepositoryData(moviesPage)
        } else {
            val storageData = MoviesStorage().getMoviesPage()
            if (storageData.movies.isEmpty()){

                RepositoryData<MoviesPage>(
                    null,
                    RepositoryErrors.UNKNOWN.message(response.error)
                )
            } else {
                RepositoryData(storageData)
            }
        }
    }
}