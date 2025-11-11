package com.loc.worldcuisine.presentation.ui.meallist

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
        onMealSelected = onMealSelected
    )
}

@Composable
private fun MealListScreenContent(
    state: MealListState,
    onMealSelected: (String) -> Unit
) {
    val gradientBackground = Brush.verticalGradient(
        listOf(Color(0xFF8E2DE2), Color(0xFF4A00E0)) // mor tonlu gradient
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(12.dp)
    ) {
        when {
            state.error != null -> {
                Text(
                    text = "⚠️ ${state.error}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            state.isLoading -> {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            state.meals.isEmpty() -> {
                Text(
                    text = "Bu ülkeye ait yemek bulunamadı 🍽️",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.meals.size) { index ->
                        AnimatedMealCard(
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
fun AnimatedMealCard(
    meal: Meal,
    onClick: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.97f else 1f, label = "")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null, // Ripple’ı kaldırmak istersen null bırak
                onClick = { onClick(meal.id) }
            )
            .padding(horizontal = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFDF7FF)
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = meal.thumbnail,
                contentDescription = meal.name,
                modifier = Modifier
                    .size(90.dp)
                    .background(Color.LightGray, RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = meal.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A148C)
                    )
                )
                Text(
                    text = "Tap to explore ✨",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Gray
                    )
                )
            }
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
