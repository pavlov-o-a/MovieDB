package com.companyname.common.entities

data class RepositoryData<T>(val data: T?, val error: RepositoryErrors? = null)