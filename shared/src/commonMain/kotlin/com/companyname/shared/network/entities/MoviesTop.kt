package com.companyname.shared.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesTop(
    val page: Int? = -1,
    @SerialName("total_pages") val allPages: Int? = -1,
    val results: List<BaseMovieResponse>? = null
)