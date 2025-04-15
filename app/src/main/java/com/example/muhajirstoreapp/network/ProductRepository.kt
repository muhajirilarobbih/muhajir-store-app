package com.example.muhajirstoreapp.network// com.example.muhajirstoreapp.network.ProductRepository.kt
import com.example.muhajirstoreapp.model.Product
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getProducts(): Response<List<Product>> {
        return apiService.getProducts()
    }
}