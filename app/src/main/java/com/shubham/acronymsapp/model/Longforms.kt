package com.shubham.acronymsapp.model

import com.google.gson.annotations.SerializedName

data class Longforms(
    @SerializedName("lf") var lf : String? = null,
    @SerializedName("freq") var freq : Int? = null,
    @SerializedName("since") var since : Int? = null,
)
