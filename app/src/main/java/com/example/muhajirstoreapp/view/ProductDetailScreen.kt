// ProductDetailScreen.kt
package com.example.muhajirstoreapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import com.example.muhajirstoreapp.R
import com.example.muhajirstoreapp.model.Product
import com.example.muhajirstoreapp.viewmodel.CartViewModel

@Composable
fun ProductDetailScreen(
    product: Product,
    onBackClick: () -> Unit,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    // State untuk mengontrol visibilitas dialog
    var showSuccessDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBackClick() }
            )
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* Handle favorite action */ }
            )
        }

        // Gambar Produk
        AsyncImage(
            model = product.image,
            contentDescription = product.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp))
                .align(Alignment.CenterHorizontally),
            error = painterResource(id = R.drawable.ic_error_image)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nama dan Harga
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = product.title.takeIf { it.isNotBlank() } ?: "Unknown Product",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .widthIn(max = 200.dp)
            )
            Text(
                text = "$${product.price}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFA500)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Berat (placeholder)
        Text(
            text = "1 lb package",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Deskripsi
        Text(
            text = "Description",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.description.takeIf { it.isNotBlank() } ?: "No description available",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.weight(1f))

        // Tombol Add to Bag
        Button(
            onClick = {
                showSuccessDialog = true
                cartViewModel.addToCart(product) // Tambahkan produk ke keranjang
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Add to Bag",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // Dialog Sukses
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                title = {
                    Text(
                        text = "Success",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                text = {
                    Text(
                        text = "Product successfully added to cart",
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                confirmButton = {
                    Button(
                        onClick = { showSuccessDialog = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50) // Warna hijau
                        )
                    ) {
                        Text(
                            text = "OK",
                            color = Color.White
                        )
                    }
                },
                dismissButton = {}
            )
        }
    }
}

@Composable
fun NutritionItem(label: String, unit: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = unit,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}