package com.loc.worldcuisine.domain.repository

import com.loc.worldcuisine.data.local.SavedMealDao
import com.loc.worldcuisine.data.local.SavedMealEntity
import com.loc.worldcuisine.data.remote.api.CuisineApi
import com.loc.worldcuisine.data.remote.dto.toDomain
import com.loc.worldcuisine.data.remote.mapper.toDomain
import com.loc.worldcuisine.domain.model.Category
import com.loc.worldcuisine.domain.model.Meal
import com.loc.worldcuisine.domain.model.MealDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CuisineRepositoryImpl @Inject constructor(
    private val api: CuisineApi,
    private val savedMealDao: SavedMealDao
) : CuisineRepository {

    // API'den gelen yemekleri alıyoruz (ör: Türk mutfağı, İtalyan mutfağı vs.)
    override suspend fun getMealsByCuisine(cuisine: String): List<Meal> {
        val response = api.getMealsByCuisine(cuisine)
        return response.meals?.map { it.toDomain() } ?: emptyList()
    }

    // Belirli bir yemeğin detayını getiriyoruz (ör: mealId=52772 -> "Teriyaki Chicken")
    override suspend fun getMealDetail(mealId: String): MealDetail {
        val response = api.getMealDetail(mealId)
        return response.meals?.firstOrNull()?.toDomain()
            ?: throw Exception("Meal not found") // null gelirse hata fırlatıyoruz
    }

    // Kullanıcı bir yemeği favorilere kaydetmek istediğinde çalışır
    override suspend fun saveMeal(meal: Meal) {
        val entity = SavedMealEntity(
            id = meal.id,
            name = meal.name,
            thumbnail = meal.thumbnail
        )
        savedMealDao.insertMeal(entity)
    }

    // Favori yemekleri listelemek için (Room DB'den Flow döner)
    override fun getSavedMeals(): Flow<List<Meal>> {
        return savedMealDao.getSavedMeals().map { entities ->
            entities.map { entity ->
                Meal(
                    id = entity.id,
                    name = entity.name,
                    thumbnail = entity.thumbnail
                )
            }
        }
    }

    // Kullanıcı bir favori yemeği silmek istediğinde
    override suspend fun deleteMeal(mealId: String) {
        savedMealDao.deleteMeal(mealId)
    }

    override suspend fun getMealCategories(): List<Category> {
        val response = api.getMealCategories() // retrofit ile çağırıyoruz
        return response.categories.map { it.toDomain() }
    }




    override suspend fun getMealsByCategory(category: String): List<Meal> {
        val response = api.getMealsByCategory(category)
        return response.meals?.map { it.toDomain() } ?: emptyList()
    }

}
