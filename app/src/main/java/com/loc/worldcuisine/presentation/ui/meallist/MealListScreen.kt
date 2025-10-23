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
import com.loc.worldcuisine.domain.model.Category
import com.loc.worldcuisine.domain.model.Meal

@Composable
fun MealListScreen(
    country: String,
    viewModel: MealListViewModel = hiltViewModel(),
    onMealSelected: (String) -> Unit
) {
    val state by viewModel.state

    LaunchedEffect(country) {
        viewModel.getMealsByCountry(country)
    }

    MealListScreenContent(
        state = state,
        onMealSelected = onMealSelected,
//        onCategorySelected = { category ->
//            viewModel.getMealsByCategory(category)
//        }
    )
}

@Composable
private fun MealListScreenContent(
    state: MealListState,
    onMealSelected: (String) -> Unit,
   // onCategorySelected: (String) -> Unit
) {
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
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 40.dp)
                )
            }
            state.meals.isEmpty() -> {
                Text(
                    text = "Bu ülkeye ait yemek bulunamadı.",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            else -> {
                LazyColumn {
                    items(state.meals.size) { index ->
                        MealItem2(
                            meal = state.meals[index],
                            onClick = { mealId -> onMealSelected(mealId) }
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

@Preview(showBackground = true)
@Composable
fun MealListScreenPreview() {
    val fakeState = MealListState(
        categories = listOf(
            Category(
                id = "1",
                name = "breef",
                thumbnail = "https://www.themealdb.com/images/category/italian.png",
                description = "Delicious Italian cuisine"
            ),
            Category(
                id = "2",
                name = "breakfest",
                thumbnail = "https://www.themealdb.com/images/category/turkish.png",
                description = "Famous Turkish dishes"
            ),
            Category(
                id = "3",
                name = "chicken",
                thumbnail = "https://www.themealdb.com/images/category/japanese.png",
                description = "Traditional Japanese meals"
            )
        ),
        meals = listOf(
            Meal(
                id = "1",
                name = "Spaghetti Carbonara",
                thumbnail = "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg"
            ),
            Meal(
                id = "2",
                name = "Adana Kebab",
                thumbnail = "https://www.themealdb.com/images/media/meals/urypqf1557110459.jpg"
            )
        ),
        selectedCategory = "Italian",
        error = null
    )

    MealListScreenContent(
        state = fakeState,
        onMealSelected = {},
       // onCategorySelected = {}
    )
}
