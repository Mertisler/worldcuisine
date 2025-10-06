package com.loc.worldcuisine.presentation.ui.cusinies


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.worldcuisine.domain.model.Meal
import com.loc.worldcuisine.domain.usecase.GetMealsByCuisineUseCase
import com.loc.worldcuisine.domain.usecase.GetSavedMealsUseCase
import com.loc.worldcuisine.domain.usecase.SaveMealUseCase
import com.loc.worldcuisine.domain.usecase.DeleteMealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CuisineViewModel @Inject constructor(
    private val getMealsByCuisineUseCase: GetMealsByCuisineUseCase,
    private val getSavedMealsUseCase: GetSavedMealsUseCase,
    private val saveMealUseCase: SaveMealUseCase,
    private val deleteMealUseCase: DeleteMealUseCase
) : ViewModel() {

    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals: StateFlow<List<Meal>> = _meals

    private val _savedMeals = MutableStateFlow<List<Meal>>(emptyList())
    val savedMeals: StateFlow<List<Meal>> = _savedMeals

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // 🔹 1. API'den belirli bir mutfağa göre yemekleri getir
    fun getMealsByCuisine(cuisine: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = getMealsByCuisineUseCase(cuisine)
                _meals.value = result
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 🔹 2. Kaydedilen yemekleri (Room DB) getir
    fun loadSavedMeals() {
        viewModelScope.launch {
            getSavedMealsUseCase().collect { savedList ->
                _savedMeals.value = savedList
            }
        }
    }

    // 🔹 3. Yemeği kaydet
    fun saveMeal(meal: Meal) {
        viewModelScope.launch {
            saveMealUseCase(meal)
        }
    }

    // 🔹 4. Yemeği sil
    fun deleteMeal(mealId: String) {
        viewModelScope.launch {
            deleteMealUseCase(mealId)
        }
    }
}
