package com.loc.worldcuisine.data.remote.api

import com.loc.worldcuisine.data.remote.dto.CuisinesResponseDto
import com.loc.worldcuisine.data.remote.dto.MealDetailResponseDto
import com.loc.worldcuisine.data.remote.dto.MealsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CuisineApi {

    // Alanlar (areas / cuisines) listesini getirir
    // https://www.themealdb.com/api/json/v1/1/list.php?a=list
    @GET("list.php?a=list")
    suspend fun getCuisines(): CuisinesResponseDto

    // Seçilen mutfağın (area) yemeklerini getirir
    // https://www.themealdb.com/api/json/v1/1/filter.php?a=Turkish
    @GET("filter.php")
    suspend fun getMealsByCuisine(
        @Query("a") cuisine: String
    ): MealsResponseDto

    // Yemeğin detayını getirir (id ile)
    // https://www.themealdb.com/api/json/v1/1/lookup.php?i=52772
    @GET("lookup.php")
    suspend fun getMealDetail(
        @Query("i") mealId: String
    ): MealDetailResponseDto
}
