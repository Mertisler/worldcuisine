package com.loc.worldcuisine.presentation.ui.mealDetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.worldcuisine.domain.usecase.GetMealDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val getMealDetailUseCase: GetMealDetailUseCase,
    private val  savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(MealDetailState())
    val state: State<MealDetailState> = _state

    init {

        savedStateHandle.get<String>("mealId")?.let { mealId ->
            if (mealId.isNotBlank()) {
                getMealDetail(mealId)
            } else {
                // Eğer ID boş gelirse (beklenmedik durum)
                _state.value = MealDetailState(error = "Geçersiz Yemek ID'si")
            }
        } ?: run {
            // Eğer "mealId" argümanı rotada hiç bulunamazsa
            _state.value = MealDetailState(error = "Yemek ID'si bulunamadı")
        }
    }

    /**
     * Belirtilen ID'ye sahip yemeğin detaylarını çeken ana fonksiyon.
     */
    private fun getMealDetail(mealId: String) {
        viewModelScope.launch {
            _state.value = MealDetailState(isLoading = true)
            try {
                // Repository yerine UseCase'i çağır
                val mealDetail = getMealDetailUseCase(mealId)
                _state.value = MealDetailState(isLoading = false, meal = mealDetail)
            } catch (e: Exception) {
                _state.value = MealDetailState(isLoading = false, error = e.message)
            }
        }
    }
}