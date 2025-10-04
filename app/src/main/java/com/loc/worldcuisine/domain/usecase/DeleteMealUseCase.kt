package com.loc.worldcuisine.domain.usecase

import com.loc.worldcuisine.domain.repository.CuisineRepository
import javax.inject.Inject

class DeleteMealUseCase @Inject constructor(
    private val repository: CuisineRepository
) {
    suspend operator fun invoke(mealId: String) {
        repository.deleteMeal(mealId)
    }
}
