// MainActivity.kt
package com.example.muhajirstoreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.muhajirstoreapp.model.Product
import com.example.muhajirstoreapp.network.UserRepository
import com.example.muhajirstoreapp.view.CartScreen
import com.example.muhajirstoreapp.view.HomeScreen
import com.example.muhajirstoreapp.view.LoginScreen
import com.example.muhajirstoreapp.view.ProductDetailScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var startDestination by remember { mutableStateOf("login") }
            // Periksa apakah ada pengguna yang login
            LaunchedEffect(Unit) {
                userRepository.loggedInUser.collectLatest { user ->
                    startDestination = if (user != null) "home" else "login"
                }
            }
            AppNavigation(startDestination)
        }
    }
}

@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination // Ubah ke login jika ini layar awal
    ) {
        composable("login") {
            // Ganti dengan layar login Anda, misalnya:
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        // Bersihkan back stack agar tidak bisa kembali ke login
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            HomeScreen(
                onProductClick = { product ->
                    try {
                        val productJson = Json.encodeToString(product)
                        val encodedProductJson = productJson
                            .replace("/", "%2F")
                            .replace(" ", "%20") // Encode spasi
                            .replace("\"", "%22") // Encode tanda kutip
                        println("Navigating with productJson: $encodedProductJson")
                        navController.navigate("productDetail/$encodedProductJson")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                onCartClick = { // Tambahkan navigasi ke CartScreen
                    navController.navigate("cart")
                },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true } // Kembali ke login dan hapus home dari back stack
                    }
                }
            )
        }
        composable(
            route = "productDetail/{productJson}",
            arguments = listOf(navArgument("productJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val productJson = backStackEntry.arguments?.getString("productJson")
                ?.replace("%2F", "/")
                ?.replace("%20", " ")
                ?.replace("%22", "\"") ?: ""
            println("Received productJson: $productJson")

            // Tampilkan UI berdasarkan hasil
            when (val result = deserializeProduct(productJson)) {
                is Result.Success -> ProductDetailScreen(
                    product = result.data,
                    onBackClick = { navController.popBackStack() }
                )
                is Result.Error -> Text("Error loading product details: ${result.message}")
            }
        }

        composable("cart") {
            CartScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

// Fungsi non-composable untuk deserialisasi
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}

fun deserializeProduct(productJson: String): Result<Product> {
    return try {
        val product = Json.decodeFromString<Product>(productJson)
        Result.Success(product)
    } catch (e: Exception) {
        e.printStackTrace()
        Result.Error("Error loading product details: ${e.message}")
    }
}