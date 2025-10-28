package com.loc.worldcuisine.presentation.ui.mealDetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.worldcuisine.domain.model.Meal
import com.loc.worldcuisine.domain.usecase.GetMealDetailUseCase
import com.loc.worldcuisine.domain.usecase.GetSavedMealsUseCase
import com.loc.worldcuisine.domain.usecase.SaveMealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val getMealDetailUseCase: GetMealDetailUseCase,
    private val  savedStateHandle: SavedStateHandle,

    private val saveMealUseCase: SaveMealUseCase,
    private val getSavedMealsUseCase: GetSavedMealsUseCase
): ViewModel() {

    private val _state = mutableStateOf(MealDetailState())
    val state: State<MealDetailState> = _state

    init {

        savedStateHandle.get<String>("mealId")?.let { mealId ->
            if (mealId.isNotBlank()) {
                getMealDetail(mealId)
                observeSavedStatus(mealId)
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

    private fun observeSavedStatus(mealId: String) {
        getSavedMealsUseCase()
            .onEach { savedMealsList ->
                val isCurrentlySaved = savedMealsList.any { it.id == mealId }
                _state.value = _state.value.copy(isSaved = isCurrentlySaved)
            }
            .launchIn(viewModelScope)
    }

    fun onSaveMeal() {
        viewModelScope.launch {
            val mealDetail = _state.value.meal ?: return@launch
            val isCurrentlySaved = _state.value.isSaved

            // Sadece zaten kayıtlı değilse kaydet
            if (!isCurrentlySaved) {
                val mealToSave = Meal(
                    id = mealDetail.id,
                    name = mealDetail.name,
                    thumbnail = mealDetail.thumbnail ?: ""
                )
                saveMealUseCase(mealToSave)
            }
            // Kayıtlıysa hiçbir şey yapma.
        }
    }
}