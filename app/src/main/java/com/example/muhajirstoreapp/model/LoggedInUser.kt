// LoggedInUser.kt
package com.example.muhajirstoreapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logged_in_user")
data class LoggedInUser(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // ID unik, hanya boleh ada 1 user yang login
    val username: String,
    val email: String
)