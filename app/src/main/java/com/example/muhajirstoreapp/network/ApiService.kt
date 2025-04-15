package com.example.muhajirstoreapp.network// com.example.muhajirstoreapp.network.ApiService.kt
import com.example.muhajirstoreapp.model.Product
import retrofit2.http.GET
import retrofit2.Response

interface ApiService {
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>
}