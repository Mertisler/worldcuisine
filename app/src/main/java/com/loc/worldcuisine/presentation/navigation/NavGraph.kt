package com.loc.worldcuisine.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.loc.worldcuisine.presentation.navigation.Routes.MEAL_DETAIL_SCREEN
import com.loc.worldcuisine.presentation.ui.cusinies.CuisineScreen
import com.loc.worldcuisine.presentation.ui.meallist.MealListScreen
import com.loc.worldcuisine.presentation.ui.meallist.MealListViewModel
import com.loc.worldcuisine.presentation.ui.mealDetail.MealDetailScreen // 👈 YENİ İMPORT
import com.loc.worldcuisine.presentation.ui.savemeal.SavedMealsScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.CUISINE_SCREEN,
        modifier = modifier
    ) {

        // 🔹 1. Dünya Mutfakları
        composable(Routes.CUISINE_SCREEN) {
            CuisineScreen(
                // hiltViewModel kullanımı burada implicit yapıldı
                onCuisineSelected = { cuisine ->
                    navController.navigate("meal_list_screen/${Uri.encode(cuisine)}")
                },
                onGoToSaveMeal = {
                    navController.navigate(Routes.SAVE_MEAL_SCREEN)
                }

            )
        }

        // 🔹 2. Seçilen mutfak için yemek listesi
        composable(
            route = Routes.MEAL_LIST_SCREEN,
            arguments = listOf(navArgument("cuisine") { type = NavType.StringType })
        ) { backStackEntry ->
            val cuisine = backStackEntry.arguments?.getString("cuisine")?.let { Uri.decode(it) } ?: ""
            val viewModel: MealListViewModel = hiltViewModel()

            LaunchedEffect(cuisine) {
                viewModel.getMealsByCountry(cuisine)
            }

            MealListScreen(
                country = cuisine,
                viewModel = viewModel,
                onMealSelected = { mealId ->
                    // ✅ GÜNCELLENDİ: Rota adını Routes objesinden dinamik oluşturuyoruz
                    navController.navigate(MEAL_DETAIL_SCREEN.replace("{mealId}", mealId))
                }
            )
        }

        // 🔹 3. YEMEK DETAY EKRANI (YENİ EKLEME)
        composable(
            route = Routes.MEAL_DETAIL_SCREEN, // "meal_detail_screen/{mealId}"
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) {
            MealDetailScreen(
                onNavigateBack = {
                    navController.popBackStack() // Geri tuşu işlevi
                },
                // 👈 YENİ EKLENEN NAVİGASYON İŞLEMİ
                onNavigateToSavedMeals = {
                    navController.navigate(Routes.SAVE_MEAL_SCREEN)
                }
            )
        }

        composable(route = Routes.SAVE_MEAL_SCREEN) {
            SavedMealsScreen(

                onMealSelected = { mealId ->

                    navController.navigate(MEAL_DETAIL_SCREEN.replace("{mealId}", mealId))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}


object Routes {
    const val CUISINE_SCREEN = "cuisine_screen"
    const val MEAL_LIST_SCREEN = "meal_list_screen/{cuisine}"
    const val MEAL_DETAIL_SCREEN = "meal_detail_screen/{mealId}"
    const val SAVE_MEAL_SCREEN = "save_meal_screen"
}

