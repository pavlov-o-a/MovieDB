package com.companyname.shared.entities

data class RepositoryData<T>(val data: T?, val error: RepositoryError? = null)