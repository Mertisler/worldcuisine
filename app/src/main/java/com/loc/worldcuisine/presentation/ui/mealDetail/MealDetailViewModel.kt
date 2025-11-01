package com.loc.worldcuisine.presentation.ui.mealDetail

import android.util.Log
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    /**
     * YENİ: Bu fonksiyon artık işlem bittiğinde çağrılacak bir
     * 'onSaveComplete' callback'i alıyor.
     */
    fun onSaveMeal(onSaveComplete: () -> Unit) { //  PARAMETRE EKLENDİ
        Log.d("SaveMealDebug", "onSaveMeal fonksiyonu çağrıldı.")

        viewModelScope.launch {
            val mealDetail = _state.value.meal ?: run {
                Log.e("SaveMealDebug", "MealDetail null, işlem iptal.")
                return@launch
            }
            val isCurrentlySaved = _state.value.isSaved

            if (!isCurrentlySaved) {
                Log.d("SaveMealDebug", "Kayıt işlemi başlıyor...")
                val mealToSave = Meal(
                    id = mealDetail.id,
                    name = mealDetail.name,
                    thumbnail = mealDetail.thumbnail ?: ""
                )

                //  'saveMealUseCase' asenkron bir suspend fonksiyonudur.
                // Kod, bu satırın bitmesini BEKLER.
                saveMealUseCase(mealToSave)

                Log.d("SaveMealDebug", "Kayıt tamamlandı.")

            } else {
                Log.d("SaveMealDebug", "Yemek zaten kayıtlıydı.")
            }

            //  İŞLEM BİTTİKTEN SONRA (ister kaydedilmiş olsun, ister olmasın)
            // Navigasyonun UI thread'de yapılması için Main context'e geç
            withContext(Dispatchers.Main) {
                onSaveComplete()
            }
        }
    }

}