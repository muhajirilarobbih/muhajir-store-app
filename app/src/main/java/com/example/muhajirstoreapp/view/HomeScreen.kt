package com.example.muhajirstoreapp.view// com.example.muhajirstoreapp.view.HomeScreen.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.muhajirstoreapp.model.Product
import com.example.muhajirstoreapp.viewmodel.LoginViewModel
import com.example.muhajirstoreapp.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
    onProductClick: (Product) -> Unit,
    onCartClick: () -> Unit,
    onLogout: () -> Unit
) {
    val categories = viewModel.categories
    val selectedCategory = viewModel.selectedCategory
    val filteredProducts = viewModel.filteredProducts
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    // State untuk teks pencarian
    var searchText by remember { mutableStateOf("") }

    // Filter produk berdasarkan teks pencarian
    val displayedProducts = if (searchText.isEmpty()) {
        filteredProducts
    } else {
        filteredProducts.filter {
            it.title.contains(searchText, ignoreCase = true)
        }
    }

    // State untuk mengontrol visibilitas bottom sheet
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    // Ambil data pengguna yang login
    val userState by loginViewModel.loggedInUser.collectAsState(initial = null)

    // Ambil data pengguna yang login
    val user = loginViewModel.loggedInUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        // Toolbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { showBottomSheet = true }
            )
            Text(
                text = "Muhajir Product App",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Cart",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onCartClick() }
            )
        }

        // Search Bar
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Search for a product") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Green,
                unfocusedIndicatorColor = Color.Green
            )
        )

        Text(
            text = "Category :",
            modifier = Modifier.padding(start = 4.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (categories.isNotEmpty()) {
                categories.forEach { category ->
                    TabItem(
                        text = category,
                        isSelected = category == selectedCategory,
                        onClick = { viewModel.selectCategory(category) }
                    )
                }
            }
        }

        // Product Grid
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else if (displayedProducts.isEmpty()) {
            Text(
                text = "No products found",
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(displayedProducts.size) { index ->
                    ProductItem(
                        product = displayedProducts[index],
                        onClick = { onProductClick(displayedProducts[index]) }
                    )
                }
            }
        }
        // Bottom Sheet untuk Profil
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                containerColor = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Ikon Profil Besar
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(bottom = 16.dp),
                        tint = Color(0xFF4CAF50)
                    )

                    // Informasi Pengguna
                    if (userState != null) {
                        Text(
                            text = userState!!.username,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = userState!!.email,
                            fontSize = 16.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    } else {
                        Text(
                            text = "User not logged in",
                            fontSize = 16.sp,
                            color = Color.Red,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    // Tombol Logout (opsional)
                    Button(
                        onClick = {
                            loginViewModel.logout() // Reset user
                            showBottomSheet = false
                            onLogout() // Navigasi ke layar login
                            // Tambahkan logika logout jika diperlukan
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFA500)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Logout",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TabItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .background(color = if (isSelected) Color.Green else Color.Gray),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = " " + text.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } + " ",
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else Color.White,
        )
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(2.dp)
                    .background(Color.Black)
            )
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
        ,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.title.take(25) + if (product.title.length > 25) "..." else "",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${product.price}",
                fontSize = 14.sp,
                color = Color(0xFFFFA500)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}