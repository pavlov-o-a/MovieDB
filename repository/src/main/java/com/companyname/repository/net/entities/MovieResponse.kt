package com.companyname.repository.net.entities

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("original_title") val title: String?,
    @SerializedName("overview") val description: String?,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("imdb_id") val imdb: String?,
    val genres: List<GenreResponse>,
    val id: Int?
)