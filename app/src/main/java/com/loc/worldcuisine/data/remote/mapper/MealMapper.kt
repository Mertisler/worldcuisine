package com.loc.worldcuisine.data.remote.mapper

import com.loc.worldcuisine.data.remote.dto.MealDetailDto
import com.loc.worldcuisine.data.remote.dto.MealDto
import com.loc.worldcuisine.domain.model.Meal
import com.loc.worldcuisine.domain.model.MealDetail


// Basit Meal mapper (liste gösterim için)
fun MealDto.toDomain(): Meal =
    Meal(
        id = this.idMeal.orEmpty(),
        name = this.strMeal.orEmpty(),
        imageUrl = this.strMealThumb.orEmpty()
    )

// Detaylı mapping (ingredient/measure çiftlerini çıkarır)
fun MealDetailDto.toDomain(): Meal {
    // ingredient / measure çiftlerini oluştur
    val ingPairs = listOf(
        strIngredient1 to strMeasure1,
        strIngredient2 to strMeasure2,
        strIngredient3 to strMeasure3,
        strIngredient4 to strMeasure4,
        strIngredient5 to strMeasure5,
        strIngredient6 to strMeasure6,
        strIngredient7 to strMeasure7,
        strIngredient8 to strMeasure8,
        strIngredient9 to strMeasure9,
        strIngredient10 to strMeasure10,
        strIngredient11 to strMeasure11,
        strIngredient12 to strMeasure12,
        strIngredient13 to strMeasure13,
        strIngredient14 to strMeasure14,
        strIngredient15 to strMeasure15,
        strIngredient16 to strMeasure16,
        strIngredient17 to strMeasure17,
        strIngredient18 to strMeasure18,
        strIngredient19 to strMeasure19,
        strIngredient20 to strMeasure20
    ).mapNotNull { (ingredient, measure) ->
        val ing = ingredient?.trim()
        val meas = measure?.trim()
        // ingredient boş değilse ekle (ölümlü boşluk, null veya "")
        if (!ing.isNullOrBlank()) {
            ing to (meas ?: "")
        } else null
    }

    val tagsList = this.strTags?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() } ?: emptyList()

    return MealDetail(
        id = this.idMeal.orEmpty(),
        name = this.strMeal.orEmpty(),
        category = this.strCategory,
        area = this.strArea,
        instructions = this.strInstructions,
        imageUrl = this.strMealThumb,
        tags = tagsList,
        youtubeUrl = this.strYoutube,
        ingredients = ingPairs
    )
}
