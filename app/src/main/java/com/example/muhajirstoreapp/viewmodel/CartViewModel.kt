// CartViewModel.kt
package com.example.muhajirstoreapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muhajirstoreapp.model.CartItem
import com.example.muhajirstoreapp.model.Product
import com.example.muhajirstoreapp.network.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {
    val cartItems: Flow<List<CartItem>> = cartRepository.allCartItems

    // Hitung total item yang dipilih
    val totalItems: Flow<Int> = cartItems.map { items ->
        items.filter { it.isSelected }.sumOf { it.quantity }
    }

    // Hitung subtotal (hanya item yang dipilih)
    val subtotal: Flow<Double> = cartItems.map { items ->
        items.filter { it.isSelected }.sumOf { it.price * it.quantity }
    }

    // Biaya pengiriman (contoh: $2.00)
    val shipping: Double = 2.00

    // Hitung total pesanan
    val orderTotal: Flow<Double> = subtotal.map { subtotal ->
        subtotal + shipping
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            cartRepository.addToCart(product)
        }
    }

    fun updateQuantity(productId: Int, newQuantity: Int) {
        viewModelScope.launch {
            cartRepository.updateQuantity(productId, newQuantity)
        }
    }

    fun updateSelection(productId: Int, isSelected: Boolean) {
        viewModelScope.launch {
            cartRepository.updateSelection(productId, isSelected)
        }
    }

    fun deleteItem(productId: Int) {
        viewModelScope.launch {
            cartRepository.deleteItem(productId)
        }
    }

    fun clearSelectedItems() {
        viewModelScope.launch {
            cartRepository.allCartItems.collect { items ->
                items.filter { it.isSelected }.forEach { cartItem ->
                    cartRepository.deleteItem(cartItem.productId)
                }
            }
        }
    }
}