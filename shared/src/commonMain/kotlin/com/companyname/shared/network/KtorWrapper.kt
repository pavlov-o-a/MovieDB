package com.companyname.shared.network

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

private const val API_KEY = "api_key"

class KtorWrapper {
    val ktorClient: HttpClient by lazy {
        HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    fun request(apiKey: String, path: String): HttpRequestBuilder {
        return HttpRequestBuilder().apply {
            url.host = "api.themoviedb.org"
            url.encodedPath = "/3/$path"
            url.parameters.append(API_KEY, apiKey)
        }
    }
}