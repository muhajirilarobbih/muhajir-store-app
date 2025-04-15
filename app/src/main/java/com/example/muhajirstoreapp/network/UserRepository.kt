// UserRepository.kt
package com.example.muhajirstoreapp.network

import com.example.muhajirstoreapp.model.LoggedInUser
import com.example.muhajirstoreapp.model.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    val loggedInUser: Flow<LoggedInUser?> = userDao.getLoggedInUser()

    suspend fun saveLoggedInUser(user: LoggedInUser) {
        userDao.insert(user)
    }

    suspend fun clearAllData() {
        userDao.clearLoggedInUser() // Hapus data pengguna
        userDao.clearCartItems() // Hapus data keranjang
    }
}