package com.companyname.shared.entities

class Movie(
    title: String,
    val description: String,
    rating: Float,
    year: String,
    val genres: List<Genre>,
    val imdb: String,
    posterPath: String,
    id: Int
) : BaseMovie(title, rating, year, posterPath, id)