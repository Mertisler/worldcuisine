package com.loc.worldcuisine.domain.usecase

import com.loc.worldcuisine.domain.model.MealDetail
import com.loc.worldcuisine.domain.repository.CuisineRepository
import javax.inject.Inject

class GetMealDetailUseCase @Inject constructor(
    private val repository: CuisineRepository
) {

    suspend operator fun invoke(mealId: String): MealDetail {
        return repository.getMealDetail(mealId)
    }
}
