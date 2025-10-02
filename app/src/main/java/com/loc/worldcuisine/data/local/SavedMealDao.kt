package com.loc.worldcuisine.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedMealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: SavedMealEntity)

    @Query("SELECT * FROM saved_meals")
    fun getSavedMeals(): Flow<List<SavedMealEntity>>

    @Query("DELETE FROM saved_meals WHERE id = :mealId")
    suspend fun deleteMeal(mealId: String)
}
