// CartScreen.kt
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.muhajirstoreapp.model.CartItem
import com.example.muhajirstoreapp.viewmodel.CartViewModel

@Composable
fun CartScreen(
    onBackClick: () -> Unit,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val cartItems by cartViewModel.cartItems.collectAsState(initial = emptyList())
    val totalItems by cartViewModel.totalItems.collectAsState(initial = 0)
    val subtotal by cartViewModel.subtotal.collectAsState(initial = 0.0)
    val shipping = cartViewModel.shipping
    val orderTotal by cartViewModel.orderTotal.collectAsState(initial = 0.0)

    // State untuk mengontrol visibilitas dialog
    var showDialog by remember { mutableStateOf(false) }

    // Ambil item yang dipilih untuk dialog
    val selectedItems = cartItems.filter { it.isSelected }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
            Text(
                text = "Cart",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Menu",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* Handle menu action */ }
            )
        }

        // Header: Total Items
        Text(
            text = "TOTAL $totalItems items",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Daftar Item
        if (cartItems.isEmpty()) {
            Text(
                text = "Your cart is empty",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(cartItems) { cartItem ->
                    CartItemRow(
                        cartItem = cartItem,
                        onQuantityChange = { newQuantity ->
                            cartViewModel.updateQuantity(cartItem.productId, newQuantity)
                        },
                        onSelectionChange = { isSelected ->
                            cartViewModel.updateSelection(cartItem.productId, isSelected)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ringkasan Harga
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SUB TOTAL",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = "$${String.format("%.2f", subtotal)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Shipping",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = "$${String.format("%.2f", shipping)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "ORDER TOTAL",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$${String.format("%.2f", orderTotal)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tombol Checkout
        Button(
            onClick = {
                if (selectedItems.isNotEmpty()) {
                    showDialog = true // Tampilkan dialog jika ada item yang dipilih
                }
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
                text = "Checkout",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // Dialog Preview Pesanan
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        text = "Order Preview",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                text = {
                    Column {
                        // Daftar Item yang Dipilih
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 200.dp)
                        ) {
                            items(selectedItems) { cartItem ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = cartItem.image,
                                        contentDescription = cartItem.title,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = cartItem.title,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Qty: ${cartItem.quantity}",
                                            fontSize = 14.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    Text(
                                        text = "$${String.format("%.2f", cartItem.price * cartItem.quantity)}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Ringkasan Harga
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "SUB TOTAL",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "$${String.format("%.2f", subtotal)}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Shipping",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "$${String.format("%.2f", shipping)}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "ORDER TOTAL",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "$${String.format("%.2f", orderTotal)}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false
                            cartViewModel.clearSelectedItems()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50)
                        )
                    ) {
                        Text(
                            text = "Confirm",
                            color = Color.White
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDialog = false }
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color.Gray
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onQuantityChange: (Int) -> Unit,
    onSelectionChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Checkbox
        Checkbox(
            checked = cartItem.isSelected,
            onCheckedChange = { isChecked ->
                onSelectionChange(isChecked)
            },
            modifier = Modifier.padding(end = 8.dp)
        )

        // Gambar Produk
        AsyncImage(
            model = cartItem.image,
            contentDescription = cartItem.title,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Nama dan Kategori
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = cartItem.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = cartItem.category,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "$${String.format("%.2f", cartItem.price)}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        // Tombol Kuantitas
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onQuantityChange(cartItem.quantity - 1) },
                modifier = Modifier
                    .size(18.dp)
                    .background(Color.LightGray, CircleShape)
            ) {
                Text(text = "-", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = cartItem.quantity.toString(),
                fontSize = 16.sp,
                modifier = Modifier.width(24.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(14.dp))
            IconButton(
                onClick = { onQuantityChange(cartItem.quantity + 1) },
                modifier = Modifier
                    .size(18.dp)
                    .background(Color(0xFF4CAF50), CircleShape)
            ) {
                Text(text = "+", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}