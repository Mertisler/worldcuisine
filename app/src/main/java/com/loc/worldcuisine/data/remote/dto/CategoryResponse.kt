// DTO'lar
package com.loc.worldcuisine.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.loc.worldcuisine.domain.model.Category

data class CategoryResponse(
    @SerializedName("meals")
    val categories: List<CategoryDto>
)

data class CategoryDto(
    @SerializedName("strCategory")
    val name: String
)
