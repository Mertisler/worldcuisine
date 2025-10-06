package com.loc.worldcuisine.di

import com.loc.worldcuisine.domain.repository.CuisineRepository
import com.loc.worldcuisine.domain.usecase.DeleteMealUseCase
import com.loc.worldcuisine.domain.usecase.GetMealDetailUseCase
import com.loc.worldcuisine.domain.usecase.GetMealsByCuisineUseCase
import com.loc.worldcuisine.domain.usecase.GetSavedMealsUseCase
import com.loc.worldcuisine.domain.usecase.SaveMealUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetMealsByCuisineUseCase(
        repository: CuisineRepository
    ): GetMealsByCuisineUseCase = GetMealsByCuisineUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMealDetailUseCase(
        repository: CuisineRepository
    ): GetMealDetailUseCase = GetMealDetailUseCase(repository)

    @Provides
    @Singleton
    fun provideSaveMealUseCase(
        repository: CuisineRepository
    ): SaveMealUseCase = SaveMealUseCase(repository)

    @Provides
    @Singleton
    fun provideGetSavedMealsUseCase(
        repository: CuisineRepository
    ): GetSavedMealsUseCase = GetSavedMealsUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteMealUseCase(
        repository: CuisineRepository
    ): DeleteMealUseCase = DeleteMealUseCase(repository)
}
