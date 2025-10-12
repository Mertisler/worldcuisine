package com.loc.worldcuisine.presentation.ui.meallist

import com.loc.worldcuisine.domain.model.Category
import com.loc.worldcuisine.domain.model.Meal

data class MealListState(
    val categories: List<Category> = emptyList(),
    val meals: List<Meal> = emptyList(),
    val selectedCategory: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
