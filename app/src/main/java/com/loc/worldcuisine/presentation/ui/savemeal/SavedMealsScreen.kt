package com.loc.worldcuisine.presentation.ui.savemeal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.loc.worldcuisine.domain.model.Meal
import com.loc.worldcuisine.presentation.ui.saved.SavedMealsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedMealsScreen(
    viewModel: SavedMealsViewModel = hiltViewModel(),
    onMealSelected: (String) -> Unit, // Detay sayfasına geri dönmek için
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kaydedilenler") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Geri Git")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> CircularProgressIndicator()
                state.error != null -> Text("Hata: ${state.error}")
                state.savedMeals.isEmpty() -> Text("Henüz kaydedilmiş yemek yok.")
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().align(Alignment.TopCenter)
                    ) {
                        items(state.savedMeals, key = { it.id }) { meal ->
                            SavedMealItem(
                                meal = meal,
                                onClick = { onMealSelected(meal.id) },
                                onDeleteClick = {
                                    viewModel.deleteSavedMeal(meal.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SavedMealItem(
    meal: Meal,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = meal.thumbnail,
                contentDescription = meal.name,
                modifier = Modifier.size(80.dp).padding(8.dp)
            )
            Text(
                text = meal.name,
                modifier = Modifier.weight(1f).padding(8.dp)
            )
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, "Sil", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}



