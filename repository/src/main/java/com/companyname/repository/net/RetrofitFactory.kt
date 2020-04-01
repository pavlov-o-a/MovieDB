package com.companyname.repository.net

import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


internal object RetrofitFactory {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "api_key"
    private const val API_KEY_VALUE = "3315326dfa126861184796dfb7d26e7e"

    private fun getRetrofit() = Retrofit.Builder()
        .client(getHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private fun getHttpClient(): OkHttpClient{
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

    fun <T> getService(service: Class<T>): T {
        return getRetrofit().create(service)
    }
}