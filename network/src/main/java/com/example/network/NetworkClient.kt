package com.example.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun createNetworkClient(
) = retrofitClient(
    httpClient(),
    gsonConverter()
)

private fun gsonConverter() =
    GsonConverterFactory.create(GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create())

private fun httpClient(): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor { chain ->
            val builder = chain.request().newBuilder()
            chain.proceed(builder.build())
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

private fun retrofitClient(
    httpClient: OkHttpClient,
    gsonConverter: GsonConverterFactory
): Retrofit =
    Retrofit.Builder()
        .baseUrl("http://192.168.15.6:8080/")
        .client(httpClient)
        .addConverterFactory(gsonConverter)
        .build()
