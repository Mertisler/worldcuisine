package com.loc.worldcuisine.presentation.ui.meallist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.worldcuisine.domain.repository.CuisineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealListViewModel @Inject constructor(
    private val repository: CuisineRepository
) : ViewModel() {

    private val _state = mutableStateOf(MealListState())
    val state: State<MealListState> = _state

//    init {
//        getCategories()
//    }

    private fun getCategories() {
        viewModelScope.launch {
            try {
                val categories = repository.getMealCategories()
                _state.value = _state.value.copy(
                    categories = categories,
                    selectedCategory = categories.firstOrNull()?.name
                )
                getMealsByCategory(categories.firstOrNull()?.name ?: "")
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun getMealsByCategory(cuisine: String) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)

                // 🔹 cuisine parametresine göre veri çek
                val meals = repository.getMealsByCategory(cuisine)

                _state.value = _state.value.copy(
                    meals = meals,
                    selectedCategory = cuisine,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.localizedMessage
                )
            }
        }
    }

    fun getMealsByCountry(country: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                val meals = repository.getMealsByCountry(country)
                _state.value = _state.value.copy(
                    meals = meals,
                    selectedCategory = null,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}

