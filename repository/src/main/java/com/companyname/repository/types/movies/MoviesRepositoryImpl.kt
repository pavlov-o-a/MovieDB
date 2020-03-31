package com.companyname.repository.types.movies

import com.companyname.common.entities.RepositoryData
import com.companyname.common.entities.RepositoryErrors
import com.companyname.repository.types.movies.net.MoviesService
import com.companyname.repository.types.movies.storage.MoviesStorage
import com.companyname.repository.net.Mapper.toMoviesPage
import com.companyname.common.entities.MoviesPage


internal class MoviesRepositoryImpl: MoviesRepository {
    override suspend fun getMovies(page: Int): RepositoryData<MoviesPage> {
        val fromNet = MoviesService().getMovies(page)?.toMoviesPage()
        return if (fromNet != null && fromNet.movies.isNotEmpty()){
            MoviesStorage().saveMoviesPage(fromNet)
            RepositoryData(fromNet)
        } else {
            val storageData = MoviesStorage().getMoviesPage()
            if (storageData.movies.isEmpty()){
                RepositoryData<MoviesPage>(
                    null,
                    RepositoryErrors.UNKNOWN
                )
            } else {
                RepositoryData(storageData)
            }
        }
    }
}