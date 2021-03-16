package com.companyname.catalog.presentation.view.adapter

enum class AdapterType {
    FULL,
    LIGHT
}

fun generateAdapterType(showImages: Boolean): AdapterType {
    return if (showImages) AdapterType.FULL else AdapterType.LIGHT
}