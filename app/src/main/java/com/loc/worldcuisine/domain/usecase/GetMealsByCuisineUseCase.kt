package com.loc.worldcuisine.domain.usecase

import com.loc.worldcuisine.domain.model.Meal
import com.loc.worldcuisine.domain.repository.CuisineRepository
import javax.inject.Inject

class GetMealsByCuisineUseCase @Inject constructor(
    private val repository: CuisineRepository
) {
    suspend operator fun invoke(cuisine: String): List<Meal> {
        return repository.getMealsByCuisine(cuisine)
    }
}
