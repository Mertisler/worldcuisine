package com.loc.worldcuisine.presentation.ui.cusinies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
    onCuisineSelected: (String) -> Unit
) {
   //val cuisines by viewModel.cuisines.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    CuisineScreenContent(
        cuisines = listOf("Turkish", "Italian", "Japanese", "Mexican", "Indian"),

        isLoading = isLoading,
        errorMessage = errorMessage,
        onCuisineSelected = onCuisineSelected
    )
}


@Composable
private fun CuisineScreenContent(
    cuisines: List<String>,
    isLoading: Boolean,
    errorMessage: String?,
    onCuisineSelected: (String) -> Unit
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

        LazyColumn {
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

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CuisineScreenPreview() {
    // Sahte (mock) state
    val fakeCuisines = listOf("Turkish", "Italian", "Japanese", "Mexican", "Indian")

    CuisineScreenContent(
        cuisines = fakeCuisines,
        isLoading = false,
        errorMessage = null,
        onCuisineSelected = {}
    )
}
