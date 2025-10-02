package com.loc.worldcuisine.domain.repository

import com.loc.worldcuisine.domain.model.Meal
import kotlinx.coroutines.flow.Flow

interface CuisineRepository {
    suspend fun getMealsByCuisine(cuisine: String): List<Meal>
    suspend fun getMealDetail(mealId: String): Meal

    suspend fun saveMeal(meal: Meal)
    fun getSavedMeals(): Flow<List<Meal>>
    suspend fun deleteMeal(mealId: String)
}
