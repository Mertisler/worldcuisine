package com.loc.worldcuisine.data.local



import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [SavedMealEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedMealDao(): SavedMealDao
}
