package com.loc.worldcuisine.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.loc.worldcuisine.presentation.ui.cusinies.CuisineScreen
import com.loc.worldcuisine.presentation.ui.cusinies.CuisineViewModel
import com.loc.worldcuisine.presentation.ui.meallist.MealListScreen
import com.loc.worldcuisine.presentation.ui.meallist.MealListViewModel

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
            val viewModel: CuisineViewModel = hiltViewModel()
            CuisineScreen(
                viewModel = viewModel,
                onCuisineSelected = { cuisine ->
                    navController.navigate("meal_list_screen/${Uri.encode(cuisine)}")
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

            // MealListViewModel zaten init'te kategorileri yüklüyor,
            // buradaki kategori parametresine göre yemekleri getir
            viewModel.getMealsByCategory(cuisine)

            MealListScreen(
                viewModel = viewModel,
                onMealSelected = { mealId ->
                    navController.navigate("meal_detail_screen/$mealId")
                }
            )
        }


    }
}


object Routes {
    const val CUISINE_SCREEN = "cuisine_screen"
    const val MEAL_LIST_SCREEN = "meal_list_screen/{cuisine}"
    const val MEAL_DETAIL_SCREEN = "meal_detail_screen/{mealId}"
}

