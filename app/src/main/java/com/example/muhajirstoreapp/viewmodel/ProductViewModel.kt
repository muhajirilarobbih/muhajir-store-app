package com.example.muhajirstoreapp.viewmodel// com.example.muhajirstoreapp.viewmodel.ProductViewModel.kt
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muhajirstoreapp.model.Product
import com.example.muhajirstoreapp.network.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _allProducts: MutableState<List<Product>> = mutableStateOf(emptyList())
    val allProducts: List<Product> by _allProducts

    private val _categories: MutableState<List<String>> = mutableStateOf(emptyList())
    val categories: List<String> by _categories

    private val _selectedCategory: MutableState<String?> = mutableStateOf(null)
    val selectedCategory: String? by _selectedCategory

    private val _filteredProducts: MutableState<List<Product>> = mutableStateOf(emptyList())
    val filteredProducts: List<Product> by _filteredProducts

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: Boolean by _isLoading

    private val _errorMessage: MutableState<String?> = mutableStateOf(null)
    val errorMessage: String? by _errorMessage

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = repository.getProducts()
                if (response.isSuccessful) {
                    val products = response.body() ?: emptyList()
                    _allProducts.value = products
                    // Ambil kategori unik dari produk
                    _categories.value = products.map { it.category }.distinct()
//                    // Set kategori pertama sebagai default (opsional)
//                    if (_categories.value.isNotEmpty()) {
//                        _selectedCategory.value = _categories.value[0]
//                    }
                    // Filter produk berdasarkan kategori yang dipilih
                    updateFilteredProducts()
                } else {
                    _errorMessage.value = "Failed to load products: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        updateFilteredProducts()
    }

    private fun updateFilteredProducts() {
        val selected = _selectedCategory.value
        _filteredProducts.value = if (selected == null) {
            _allProducts.value
        } else {
            _allProducts.value.filter { it.category == selected }
        }
    }
}