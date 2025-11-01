package com.loc.worldcuisine.presentation.ui.saved

import android.util.Log
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
        Log.d("SavedMealsDebug", "SavedMealsViewModel başlatıldı (init).") //  LOG 1
        observeSavedMeals()
    }

    private fun observeSavedMeals() {
        Log.d("SavedMealsDebug", "observeSavedMeals() çağrıldı. Flow dinleniyor...") //  LOG 2
        _state.value = SavedMealsState(isLoading = true)

        getSavedMealsUseCase()
            .onEach { mealsList ->
                Log.d("SavedMealsDebug", "Flow'dan YENİ VERİ GELDİ. Liste boyutu: ${mealsList.size}")

                if (mealsList.isNotEmpty()) {
                    Log.d("SavedMealsDebug", "Gelen ilk yemeğin adı: ${mealsList.first().name}")
                    Log.d("SavedMealsDebug", "Gelen son yemek adı: ${mealsList.last().name}")
                }

                _state.value = SavedMealsState(
                    isLoading = false,
                    savedMeals = mealsList
                )
            }
            .catch { e ->
                Log.e("SavedMealsDebug", "Flow dinlerken hata: ${e.message}") //  LOG 5
                _state.value = SavedMealsState(isLoading = false, error = e.message)
            }
            .launchIn(viewModelScope)
    }
    fun deleteSavedMeal(mealId: String) {
        viewModelScope.launch {
            deleteMealUseCase(mealId)
            // Flow sayesinde liste otomatik güncellenecek
        }
    }


    }


