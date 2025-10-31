package com.loc.worldcuisine.presentation.ui.cusinies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.loc.worldcuisine.domain.model.Meal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Dünya Mutfakları",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {

                CircularProgressIndicator()
            } else if (errorMessage != null) {

                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            } else {

                LazyColumn(modifier = Modifier.fillMaxSize())
                {
                    items(cuisines.size) { index ->
                        val cuisine = cuisines[index]
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable { onCuisineSelected(cuisine) },
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = cuisine, style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onGoToSavedMeals,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text("Kaydedilenler")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CuisineScreenPreview() {

    val fakeCuisines = listOf("Turkish", "Italian", "Japanese", "Mexican", "Indian")

    CuisineScreenContent(
        cuisines = fakeCuisines,
        isLoading = false,
        errorMessage = null,
        onCuisineSelected = {},
        onGoToSavedMeals = {}
    )
}
