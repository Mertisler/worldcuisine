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

    init {
        getCategories()
    }

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

    fun getMealsByCategory(category: String) {
        viewModelScope.launch {
            try {
                val meals = repository.getMealsByCategory(category)
                _state.value = _state.value.copy(
                    meals = meals,
                    selectedCategory = category
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }
}
