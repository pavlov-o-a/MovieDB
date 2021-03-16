package com.companyname.repository.net.entities

import com.google.gson.annotations.SerializedName

data class MoviesTop(
    val page: Int? = -1,
    @SerializedName("total_pages") val allPages: Int? = -1,
    val results: List<BaseMovieResponse>? = null
)