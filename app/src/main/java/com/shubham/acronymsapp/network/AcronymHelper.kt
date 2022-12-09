package com.shubham.acronymsapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AcronymHelper {

    private val okhttplogging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okhttp = OkHttpClient.Builder()
        .addInterceptor(okhttplogging)
        .build()

    val baseUrl = "http://www.nactem.ac.uk/"

    fun getRetrofitInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttp)
            .build()
    }
}