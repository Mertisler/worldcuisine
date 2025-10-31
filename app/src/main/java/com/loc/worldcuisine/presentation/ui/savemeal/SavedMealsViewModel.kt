package com.loc.worldcuisine.presentation.ui.saved

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.worldcuisine.domain.model.Meal
import com.loc.worldcuisine.domain.usecase.DeleteMealUseCase
import com.loc.worldcuisine.domain.usecase.GetSavedMealsUseCase
import com.loc.worldcuisine.presentation.ui.savemeal.SavedMealsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedMealsViewModel @Inject constructor(
    private val getSavedMealsUseCase: GetSavedMealsUseCase,
    private val deleteMealUseCase: DeleteMealUseCase
) : ViewModel() {
    private val _state = mutableStateOf(SavedMealsState())
    val state: State<SavedMealsState> = _state

    init {
        observeSavedMeals()
    }

    private fun observeSavedMeals() {
        _state.value= SavedMealsState(isLoading = true)
        getSavedMealsUseCase()
            .onEach {meals ->
                _state.value = SavedMealsState(isLoading = false, meals = meals)
            }.catch {
                _state.value = SavedMealsState(isLoading = false, error = it.message)
            }.launchIn(viewModelScope)
            }
    fun deleteSavedMeal(mealId: String) {
        viewModelScope.launch {
            deleteMealUseCase(mealId)
            // Flow sayesinde liste otomatik güncellenecek
        }
    }


    }


