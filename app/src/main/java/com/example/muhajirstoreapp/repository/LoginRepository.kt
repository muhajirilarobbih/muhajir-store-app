package com.example.muhajirstoreapp.repository

import javax.inject.Inject

interface LoginRepository {
    fun login(email: String, password: String): Boolean
}

class LoginRepositoryImpl @Inject constructor() : LoginRepository {
    override fun login(email: String, password: String): Boolean {
        // Logika login sederhana (bisa diganti dengan API call)
        return email.isNotEmpty() && password.isNotEmpty()
    }
}