package com.loc.worldcuisine.domain.usecase

import com.loc.worldcuisine.domain.model.Meal
import com.loc.worldcuisine.domain.repository.CuisineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedMealsUseCase @Inject constructor(
    private val repository: CuisineRepository
) {
    operator fun invoke(): Flow<List<Meal>> {
        return repository.getSavedMeals()
    }
}
