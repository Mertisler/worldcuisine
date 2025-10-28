package com.loc.worldcuisine.presentation.ui.mealDetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.loc.worldcuisine.domain.model.MealDetail


/**
 * ViewModel'i yöneten ve state'i (durumu) dinleyen ana Composable.
 */
@Composable
fun MealDetailScreen(
    viewModel: MealDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit // Geri gitmek için navigasyon fonksiyonu
) {
    // ViewModel'den gelen 'state'i dinle. 'by' kullanmak,
    // state değiştiğinde ekranın otomatik güncellenmesini sağlar.
    val state by viewModel.state

    // UI'ı çizen alt Composable'ı çağır
    MealDetailScreenContent(
        state = state,
        onNavigateBack = onNavigateBack
    )
}

/**
 * Gerçek UI mantığını (when bloğu) içeren Composable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MealDetailScreenContent(
    state: MealDetailState,
    onNavigateBack: () -> Unit,
    onSaveMeal: () -> Unit = {}
) {
    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    // Yemek yüklendiyse başlığa adını yaz, yüklenmediyse 'Detay' yaz
                    Text(text = state.meal?.name ?: "Yemek Detayı")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Geri Git"
                        )
                    }
                },
                        actions = {
                    // Sadece yemek yüklendiyse VE
                    // yemek henüz kayıtlı DEĞİLSE butonu göster
                    if (state.meal != null && !state.isSaved) {
                        IconButton(onClick = onSaveMeal) {
                            Icon(
                                // Sadece "Ekle" ikonu
                                imageVector = Icons.Outlined.Add,
                                contentDescription = "Kaydet"
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->

        // Box, 'when' durumlarına göre içeriği ortalamak veya doldurmak için kullanışlıdır
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center // Hata ve Yükleniyor durumları için
        ) {
            when {
                // 1. Durum: Yükleniyor
                state.isLoading -> {
                    CircularProgressIndicator()
                }

                // 2. Durum: Hata oluştu
                state.error != null -> {
                    Text(
                        text = "Hata: ${state.error}",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // 3. Durum: Başarılı (Yemek detayı geldi)
                state.meal != null -> {
                    // İçerik artık ortalanmayacağı için Box'ın hizalamasını geçersiz kıl
                    MealDetailContent(
                        meal = state.meal,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}

/**
 * Yemek detaylarını (Resim, İsim, Tarif) gösteren Composable.
 */
@Composable
private fun MealDetailContent(
    meal: MealDetail,
    modifier: Modifier = Modifier
) {
    // Tarif uzun olabileceği için kaydırılabilir bir Column kullanıyoruz
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), // Kaydırma özelliği ekle
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Yemek Resmi
        AsyncImage(
            model = meal.thumbnail,
            contentDescription = meal.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop // Resmi alana sığdır
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Yemek Adı
        Text(
            text = meal.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth() // Metni sola yasla (Column'un hizalamasını geçersiz kıl)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Mutfak / Kategori Bilgisi
        Text(
            text = "${meal.area} Mutfağı • ${meal.category} Kategorisi",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tarif Başlığı
        Text(
            text = "Hazırlanışı",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Tarif (Instructions)
        meal.instructions?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


// --- ÖNİZLEME (PREVIEW) ---



@Preview(showBackground = true)
@Composable
fun MealDetailScreenPreview() {
    // Sahte bir MealDetail nesnesi oluştur
    val fakeMeal = MealDetail(
        id = "52772",
        name = "Spicy Arrabiata Penne",
        thumbnail = "https://www.themealdb.com/images/media/meals/ustsqw1468234440.jpg",
        instructions = "Penne makarnasını paketteki talimatlara göre haşlayın. Ayrı bir tavada zeytinyağını ısıtın, doğranmış sarımsak ve pul biberi ekleyin. Sarımsaklar pembeleşince doğranmış domatesleri (veya domates püresini) ekleyin. Tuz ve karabiberle tatlandırın. Sos koyulaşana kadar pişirin. Makarnayı süzüp sosla karıştırın. Taze fesleğen yaprakları ile servis yapın.",
        area = "Italian",
        category = "Pasta",
        imageUrl ="cvvb",

        tags = listOf("Pasta", "Vegetarian"), // Varsayılan veya sahte değerler
        youtubeUrl = "https://www.youtube.com/watch?v=...", // Nullable olduğu için boş string de olabilir.
        ingredients = listOf(
            Pair("Penne", "250g"),
            Pair("Domates", "400g")
        )
    )

    // Başarılı durumu (içerik dolu) test et
    val fakeState = MealDetailState(
        meal = fakeMeal,
        isLoading = false,
        error = null
    )

    MealDetailScreenContent(
        state = fakeState,
        onNavigateBack = {} // Preview'da geri gitme işlemi boş
    )
}

@Preview(showBackground = true, name = "Hata Durumu")
@Composable
fun MealDetailScreenErrorPreview() {
    val fakeState = MealDetailState(
        meal = null,
        isLoading = false,
        error = "API'den veri çekilemedi. Bağlantı hatası."
    )

    MealDetailScreenContent(
        state = fakeState,
        onNavigateBack = {}
    )
}

@Preview(showBackground = true, name = "Yükleniyor Durumu")
@Composable
fun MealDetailScreenLoadingPreview() {
    val fakeState = MealDetailState(
        meal = null,
        isLoading = true,
        error = null
    )

    MealDetailScreenContent(
        state = fakeState,
        onNavigateBack = {}
    )
}