package com.loc.worldcuisine.domain.model

data class Meal(
    val id: String,
    val name: String,
    val thumbnail: String,
    val instructions: String? = null
)