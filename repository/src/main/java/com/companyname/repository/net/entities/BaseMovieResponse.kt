package com.companyname.repository.net.entities

import com.google.gson.annotations.SerializedName

data class BaseMovieResponse(
    @SerializedName("original_title") val title: String?,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("poster_path") val posterPath: String,
    val id: Int?
)