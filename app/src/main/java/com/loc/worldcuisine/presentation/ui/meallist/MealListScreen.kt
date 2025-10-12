package com.loc.worldcuisine.presentation.ui.meallist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.loc.worldcuisine.domain.model.Meal



@Composable
fun MealListScreen(
    viewModel: MealListViewModel = hiltViewModel(),
    onMealSelected: (String) -> Unit
) {
    val state = viewModel.state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(8.dp)
    ) {
        when {
            state.error != null -> {
                Text(
                    text = "Hata: ${state.error}",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
            state.categories.isEmpty() -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 40.dp)
                )
            }
            else -> {
                // Kategori Barı
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.categories.forEach { category ->
                        val isSelected = category.name == state.selectedCategory
                        Card(
                            modifier = Modifier
                                .clickable { viewModel.getMealsByCategory(category.name) },
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected)
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                                else
                                    Color.LightGray
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = category.name,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                color = if (isSelected) Color.White else Color.Black
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Yemek Listesi
                LazyColumn {
                    items(state.meals.size) { index ->
                        MealItem2(
                            meal = state.meals[index],
                            onClick = { mealId ->
                                onMealSelected(mealId)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MealItem2(
    meal: Meal,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(meal.id) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = meal.thumbnail,
                contentDescription = meal.name,
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
            )
            Text(
                text = meal.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
