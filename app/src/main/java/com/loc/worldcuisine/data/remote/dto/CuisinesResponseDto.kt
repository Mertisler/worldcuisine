package com.loc.worldcuisine.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CuisinesResponseDto(
    @SerializedName("meals")
    val meals: List<CuisineDto>?
)

data class CuisineDto(
    // API: "strArea"
    @SerializedName("strArea")
    val strArea: String?
)
