package `in`.surojit.bookfinderapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import `in`.surojit.bookfinderapp.ui.screens.DetailsScreen
import `in`.surojit.bookfinderapp.ui.screens.SearchScreen


@Composable
fun NavGraph(start: String = "search") {
    val navController: NavHostController = rememberNavController()

    NavHost(navController, startDestination = start) {
        composable("search") {
            SearchScreen(
                onBookClick = { book ->
                    navController.navigate("details/${book.id}")
                }
            )
        }

        composable("details/{bookId}") { backStack ->
            val bookId = backStack.arguments?.getString("bookId") ?: return@composable
            DetailsScreen(
                bookId = bookId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}