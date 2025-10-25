package com.loc.worldcuisine.domain.usecase

import com.loc.worldcuisine.domain.repository.CuisineRepository
import javax.inject.Inject

class GetCuisinesUseCase @Inject constructor(
    private val repository: CuisineRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.getCuisines()
    }
}