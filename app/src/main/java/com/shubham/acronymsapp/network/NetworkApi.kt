package com.shubham.acronymsapp.network

import com.shubham.acronymsapp.model.AcronymResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {
    @GET("software/acromine/dictionary.py")
    suspend fun getAcronyms(@Query("sf") sf : String): Response<List<AcronymResponse>>
}