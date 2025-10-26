package com.loc.worldcuisine.presentation.ui.mealDetail

import com.loc.worldcuisine.domain.model.MealDetail

data class MealDetailState(
    val meal: MealDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

