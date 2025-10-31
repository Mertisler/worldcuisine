package com.loc.worldcuisine.presentation.ui.savemeal

import com.loc.worldcuisine.domain.model.Meal

data class SavedMealsState(
    val meals: List<Meal> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val savedMeals: List<Meal> = emptyList()
)

