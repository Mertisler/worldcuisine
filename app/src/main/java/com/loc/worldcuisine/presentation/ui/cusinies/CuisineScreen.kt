@file:OptIn(ExperimentalFoundationApi::class)

package com.loc.worldcuisine.presentation.ui.cusinies

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CuisineScreen(
    viewModel: CuisineViewModel = hiltViewModel(),
    onCuisineSelected: (String) -> Unit,
    onGoToSaveMeal: () -> Unit
) {
    val cuisines by viewModel.cuisines
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    CuisineScreenContent(
        cuisines = cuisines,
        isLoading = isLoading,
        errorMessage = errorMessage,
        onCuisineSelected = onCuisineSelected,
        onGoToSavedMeals = onGoToSaveMeal
    )
}

@Composable
private fun CuisineScreenContent(
    cuisines: List<String>,
    isLoading: Boolean,
    errorMessage: String?,
    onCuisineSelected: (String) -> Unit,
    onGoToSavedMeals: () -> Unit
) {
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            MaterialTheme.colorScheme.background
        )
    )

   // Spacer(modifier=Modifier.height(16.dp))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(16.dp)
    ) {
        Spacer(modifier=Modifier.height(16.dp))
        // Başlık
        Text(
            text = "🌍 Dünya Mutfakları",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            textAlign = TextAlign.Center
        )

        when {
            isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

            errorMessage != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }

            else -> {
                // Dünya + Liste birleşimi
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 🌎 Dönen dünya
                    WorldCuisineOrbit(cuisines.take(10), onCuisineSelected)

                    Spacer(modifier = Modifier.height(16.dp))


                    Text(
                        text = "Tüm Mutfaklar",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 140.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(cuisines) { cuisine ->
                            CuisineCard(cuisine, onCuisineSelected)
                        }
                    }
                }
            }
        }



        GradientButton(
            text = "Kaydedilenler",
            icon = Icons.Default.Favorite,
            onClick = onGoToSavedMeals
        )
    }
}

//  Dünya etrafında dönen mutfaklar
@Composable
fun WorldCuisineOrbit(
    cuisines: List<String>,
    onCuisineSelected: (String) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val angleOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 20000, easing = LinearEasing)
        ),
        label = "angle"
    )

    Box(
        modifier = Modifier
            .size(260.dp)
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        // Dünya
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("🌎", fontSize = 44.sp)
        }

        // Etrafındaki mutfak isimleri
        cuisines.forEachIndexed { index, cuisine ->
            val angle = (index.toFloat() / cuisines.size) * 360f + angleOffset
            val radius = 140f
            val x = radius * cos(Math.toRadians(angle.toDouble()))
            val y = radius * sin(Math.toRadians(angle.toDouble()))

            Box(
                modifier = Modifier
                    .offset(x.dp, y.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.85f))
                    .clickable { onCuisineSelected(cuisine) }
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = cuisine,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}


@Composable
fun CuisineCard(
    cuisine: String,
    onCuisineSelected: (String) -> Unit
) {
    Card(
        onClick = { onCuisineSelected(cuisine) },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = cuisine,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun GradientButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues()
    ) {

        Box(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    ),
                    shape = CircleShape
                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon, contentDescription = null,
                    tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = text, color = Color.White, fontWeight =
                    FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CuisineScreenPreview() {
    CuisineScreenContent(
        cuisines = listOf(
            "Turkish", "Italian", "Japanese", "Mexican", "Indian",
            "French", "Spanish", "Greek", "Chinese", "Korean"
        ),
        isLoading = false,
        errorMessage = null,
        onCuisineSelected = {},
        onGoToSavedMeals = {}
    )
}
