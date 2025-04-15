// CartItem.kt
package com.example.muhajirstoreapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val productId: Int, // ID produk sebagai primary key
    val title: String,
    val price: Double,
    val image: String,
    val quantity: Int, // Kuantitas produk di keranjang
    val category: String, // Tambahkan kategori
    val isSelected: Boolean = true // Tambahkan properti isSelected, default true
)