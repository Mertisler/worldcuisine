package com.loc.worldcuisine.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


import android.content.Context
import androidx.room.Room
import com.loc.worldcuisine.data.local.AppDatabase
import com.loc.worldcuisine.data.local.SavedMealDao
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "world_cuisine_db"
        ).build()

    @Provides
    @Singleton
    fun provideSavedMealDao(database: AppDatabase): SavedMealDao =
        database.savedMealDao()
}
