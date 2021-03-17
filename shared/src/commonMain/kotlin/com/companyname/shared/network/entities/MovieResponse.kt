package com.companyname.shared.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("original_title") val title: String?,
    @SerialName("overview") val description: String?,
    @SerialName("vote_average") val rating: Float,
    @SerialName("release_date") val releaseDate: String?,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("imdb_id") val imdb: String?,
    val genres: List<GenreResponse>,
    val id: Int?
)