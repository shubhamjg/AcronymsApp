package com.shubham.acronymsapp.model

import com.google.gson.annotations.SerializedName

data class AcronymResponse(
    @SerializedName("sf") var sf  : String? = null,
    @SerializedName("lfs") var lfs : ArrayList<Longforms> = arrayListOf()
)
