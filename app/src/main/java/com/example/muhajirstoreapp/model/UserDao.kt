// UserDao.kt
package com.example.muhajirstoreapp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.muhajirstoreapp.model.LoggedInUser
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: LoggedInUser)

    @Query("SELECT * FROM logged_in_user LIMIT 1")
    fun getLoggedInUser(): Flow<LoggedInUser?>

    @Query("DELETE FROM logged_in_user")
    suspend fun clearLoggedInUser()

    @Query("DELETE FROM cart_items")
    suspend fun clearCartItems()
}