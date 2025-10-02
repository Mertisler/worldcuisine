package com.loc.worldcuisine.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_meals")
data class SavedMealEntity(
    @PrimaryKey val id: String,
    val name: String,
    val thumbnail: String
)

