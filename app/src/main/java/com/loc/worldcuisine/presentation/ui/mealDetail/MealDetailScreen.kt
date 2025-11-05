package com.loc.worldcuisine.presentation.ui.mealDetail

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.loc.worldcuisine.domain.model.MealDetail
import kotlinx.coroutines.launch

@Composable
fun MealDetailScreen(
    viewModel: MealDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToSavedMeals: () -> Unit = {}
) {
    val state by viewModel.state

    MealDetailScreenContent(
        state = state,
        onNavigateBack = onNavigateBack,
        onSaveMeal = {
            viewModel.onSaveMeal(
                onSaveComplete = { onNavigateToSavedMeals() }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MealDetailScreenContent(
    state: MealDetailState,
    onNavigateBack: () -> Unit,
    onSaveMeal: () -> Unit
) {
    // Gradient arka plan
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
    )

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.meal?.name ?: "Yemek Detayı",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Geri Dön",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    if (state.meal != null && !state.isSaved) {
                        AnimatedSaveButton(onSaveMeal)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(color = Color.White)
                }

                state.error != null -> {
                    Text(
                        text = "🚨 Hata: ${state.error}",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                state.meal != null -> {
                    MealDetailContent(meal = state.meal)
                }
            }
        }
    }
}

/**
 * Z kuşağı tarzı, modern ve enerjik içerik alanı
 */
@Composable
private fun MealDetailContent(meal: MealDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🍝 Yemek Görseli
        AsyncImage(
            model = meal.thumbnail,
            contentDescription = meal.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .clip(RoundedCornerShape(28.dp))
                .padding(top = 8.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🍽️ Sabit Bilgiler (scroll olmaz)
        Text(
            text = meal.name,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "${meal.area} Mutfağı • ${meal.category}",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.White.copy(alpha = 0.8f)
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 🧾 Scrollable Tarif Alanı
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // scroll alanını büyüt
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White.copy(alpha = 0.08f)),
            tonalElevation = 2.dp,
            color = Color.White.copy(alpha = 0.1f)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "👨‍🍳 Hazırlanışı",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = meal.instructions ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.4f
                    )
                )
            }
        }
    }
}


/**
 * Modern, animasyonlu "Kaydet" butonu
 */
@Composable
private fun AnimatedSaveButton(onSave: () -> Unit) {
    var isClicked by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope() // ✅ Compose scope
    val scale by animateFloatAsState(
        targetValue = if (isClicked) 1.3f else 1f,
        label = "scaleAnim"
    )

    IconButton(
        onClick = {
            isClicked = true
            onSave()
            scope.launch {
                kotlinx.coroutines.delay(250)
                isClicked = false
            }
        },
        modifier = Modifier
            .size(48.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFEE0979), Color(0xFFFF6A00))
                ),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.Outlined.FavoriteBorder,
            contentDescription = "Kaydet",
            tint = Color.White
        )
    }
}


// --- ÖNİZLEMELER ---
@Preview(showBackground = true)
@Composable
fun MealDetailScreenPreview() {
    val fakeMeal = MealDetail(
        id = "52772",
        name = "Spicy Arrabiata Penne",
        thumbnail = "https://www.themealdb.com/images/media/meals/ustsqw1468234440.jpg",
        instructions = "Penne makarnasını haşlayın. Sosu hazırlayın, karıştırın ve afiyetle yiyin 😋",
        area = "Italian",
        category = "Pasta",
        imageUrl = "",
        tags = listOf("Pasta", "Vegetarian"),
        youtubeUrl = "",
        ingredients = listOf("Penne" to "250g", "Domates" to "400g")
    )

    val fakeState = MealDetailState(
        meal = fakeMeal,
        isLoading = false,
        error = null
    )

    MealDetailScreenContent(
        state = fakeState,
        onNavigateBack = {},
        onSaveMeal = {}
    )
}
