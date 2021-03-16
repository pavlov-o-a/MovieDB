package com.companyname.shared.entities

data class Page<T>(
    val page: Int,
    val allPages: Int,
    val data: List<T>
)