package com.loc.worldcuisine.domain.repository

import com.loc.worldcuisine.domain.model.Meal
import com.loc.worldcuisine.domain.model.MealDetail
import kotlinx.coroutines.flow.Flow

/**
 * CuisineRepository, veri katmanıyla (API + Room DB)
 * domain/use case katmanı arasında köprü kurar.
 *
 * Burada sadece iş mantığına uygun method imzaları tanımlanır.
 * (Yani nasıl yapılacağı değil, ne yapılacağı belirtilir)
 */
interface CuisineRepository {

    /**
     * Belirli bir mutfağa (örneğin: "Turkish", "Italian") ait yemekleri getirir.
     * Kaynak: Remote API (Retrofit)
     */
    suspend fun getMealsByCuisine(cuisine: String): List<Meal>

    /**
     * Tek bir yemeğin detayını getirir (örneğin: mealId = "52772")
     * Kaynak: Remote API
     */
    suspend fun getMealDetail(mealId: String): MealDetail

    /**
     * Bir yemeği Room veritabanına (favorilere) kaydeder.
     * Kaynak: Local DB (Room)
     */

    suspend fun saveMeal(meal: Meal)

    /**
     * Kaydedilmiş (favori) yemekleri getirir.
     * Kaynak: Local DB (Room)
     */
    fun getSavedMeals(): Flow<List<Meal>>

    /**
     * Belirli bir favori yemeği siler.
     * Kaynak: Local DB (Room)
     */
    suspend fun deleteMeal(mealId: String)
}
