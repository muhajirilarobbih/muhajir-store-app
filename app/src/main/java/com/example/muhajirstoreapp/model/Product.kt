package com.example.muhajirstoreapp.model

import kotlinx.serialization.Serializable

// com.example.muhajirstoreapp.model.Product.kt
@Serializable
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
)

@Serializable
data class Rating(
    val rate: Float,
    val count: Int
)