package com.loc.worldcuisine.presentation.ui.cusinies

import com.loc.worldcuisine.domain.model.Meal

data class CuisineState(
    val isLoading: Boolean = false,
    val meals: List<Meal> = emptyList(),
    val error: String? = null
)
