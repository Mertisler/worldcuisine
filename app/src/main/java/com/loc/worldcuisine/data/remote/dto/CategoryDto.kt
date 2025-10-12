package com.loc.worldcuisine.data.remote.dto


import com.loc.worldcuisine.domain.model.Category
import com.loc.worldcuisine.data.remote.dto.CategoryDto

fun CategoryDto.toDomain(): Category {
    return Category(
        id = "",
        name = this.name,
        thumbnail = "",
        description = ""
    )
}