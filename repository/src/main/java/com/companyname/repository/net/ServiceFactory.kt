package com.companyname.repository.net

import javax.inject.Inject

class ServiceFactory @Inject constructor(private val wrapper: RetrofitWrapper) {

    fun <T> getService(service: Class<T>): T {
        return wrapper.retrofit.create(service)
    }
}