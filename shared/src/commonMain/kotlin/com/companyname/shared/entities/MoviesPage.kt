package com.companyname.shared.entities

data class MoviesPage(
    val page: Int,
    val allPages: Int,
    val movies: List<BaseMovie>
)