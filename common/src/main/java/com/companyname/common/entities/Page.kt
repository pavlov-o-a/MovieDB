package com.companyname.common.entities

data class Page<T>(
    val page: Int,
    val allPages: Int,
    val data: List<T>
)