package com.loc.worldcuisine.domain.model

data class MealDetail(
    val id: String,
    val name: String,
    val category: String?,
    val thumbnail: String?,
    val area: String?,
    val instructions: String?,
    val imageUrl: String?,
    val tags: List<String>,
    val youtubeUrl: String?,
    val ingredients: List<Pair<String, String>> // (ingredient, measure)
)

