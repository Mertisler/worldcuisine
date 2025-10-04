package com.loc.worldcuisine.domain.usecase

import com.loc.worldcuisine.domain.model.Meal
import com.loc.worldcuisine.domain.repository.CuisineRepository
import javax.inject.Inject

class GetMealDetailUseCase @Inject constructor(
    private val repository: CuisineRepository
) {
    suspend operator fun invoke(mealId: String): Meal {
        return repository.getMealDetail(mealId)
    }
}
