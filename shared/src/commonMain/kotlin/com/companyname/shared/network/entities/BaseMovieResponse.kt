package com.companyname.shared.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseMovieResponse(
    @SerialName("original_title") val title: String?,
    @SerialName("vote_average") val rating: Float,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("poster_path") val posterPath: String,
    val id: Int?
)