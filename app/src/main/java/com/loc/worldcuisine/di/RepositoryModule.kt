package com.loc.worldcuisine.di

import com.loc.worldcuisine.domain.repository.CuisineRepository
import com.loc.worldcuisine.domain.repository.CuisineRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCuisineRepository(
        impl: CuisineRepositoryImpl
    ): CuisineRepository
}
