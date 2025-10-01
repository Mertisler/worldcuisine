package com.loc.worldcuisine.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MealsResponseDto(
    @SerializedName("meals")
    val meals: List<MealDto>?
)

data class MealDto(
    @SerializedName("idMeal")
    val idMeal: String?,
    @SerializedName("strMeal")
    val strMeal: String?,
    @SerializedName("strMealThumb")
    val strMealThumb: String?
    // filter.php sonucu genelde bu üç alanı içerir
)

