package com.companyname.repository.types.movies.storage

import com.companyname.common.entities.MoviesPage

internal class MoviesStorage{

    fun getMoviesPage(): MoviesPage {
        //todo make local storage
        return MoviesPage(0,0, listOf())
    }

    fun saveMoviesPage(fromNet: MoviesPage) {
        //todo make local storage
    }
}