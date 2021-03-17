package com.companyname.shared

import io.ktor.client.*

object KtorFactory {

    fun getKtor() {
        val client = HttpClient()
    }
}