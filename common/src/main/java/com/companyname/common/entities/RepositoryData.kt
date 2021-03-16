package com.companyname.common.entities

data class RepositoryData<T>(val data: T?, val error: RepositoryError? = null)