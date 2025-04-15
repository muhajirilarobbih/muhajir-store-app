// CartRepository.kt
package com.example.muhajirstoreapp.network

import com.example.muhajirstoreapp.model.CartDao
import com.example.muhajirstoreapp.model.CartItem
import com.example.muhajirstoreapp.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {
    val allCartItems: Flow<List<CartItem>> = cartDao.getAllCartItems()

    suspend fun addToCart(product: Product) {
        // Cek apakah produk sudah ada di keranjang
        val existingItem = cartDao.getCartItemByProductId(product.id)
        if (existingItem != null) {
            // Jika produk sudah ada, update kuantitas
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            cartDao.update(updatedItem)
        } else {
            // Jika produk belum ada, tambahkan sebagai item baru
            val cartItem = CartItem(
                productId = product.id,
                title = product.title,
                price = product.price,
                image = product.image,
                quantity = 1,
                category = product.category,
                isSelected = true
            )
            cartDao.insert(cartItem)
        }
    }

    suspend fun updateQuantity(productId: Int, newQuantity: Int) {
        val item = cartDao.getCartItemByProductId(productId)
        if (item != null) {
            if (newQuantity <= 0) {
                cartDao.delete(item) // Hapus jika kuantitas menjadi 0
            } else {
                val updatedItem = item.copy(quantity = newQuantity)
                cartDao.update(updatedItem)
            }
        }
    }

    suspend fun updateSelection(productId: Int, isSelected: Boolean) {
        val item = cartDao.getCartItemByProductId(productId)
        if (item != null) {
            val updatedItem = item.copy(isSelected = isSelected)
            cartDao.update(updatedItem)
        }
    }

    suspend fun deleteItem(productId: Int) {
        val item = cartDao.getCartItemByProductId(productId)
        if (item != null) {
            cartDao.delete(item)
        }
    }
}