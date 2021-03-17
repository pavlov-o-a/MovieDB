package com.companyname.repository.net

import com.companyname.repository.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "api_key"
private const val API_KEY_VALUE = BuildConfig.API_KEY

class RetrofitFactory @Inject constructor() {
    fun getRetrofit() = Retrofit.Builder()
        .client(getHttpClient())
        .addConverterFactory(Json {
            ignoreUnknownKeys = true
        }.asConverterFactory(MediaType.parse("application/json")!!))
        .baseUrl(BASE_URL)
        .build()

    private fun getHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter(API_KEY, API_KEY_VALUE)
                .build()
            val request = original.newBuilder().url(url).build()
            chain.proceed(request)
        }
        httpClient.connectTimeout(5, TimeUnit.SECONDS)
        httpClient.readTimeout(5, TimeUnit.SECONDS)
        return httpClient.build()
    }
}