package com.shubham.acronymsapp.repository

import com.shubham.acronymsapp.network.NetworkApi

class AcronymRepository(private val api: NetworkApi) {
    suspend fun getAcronyms(sF:String) = api.getAcronyms(sF)
}