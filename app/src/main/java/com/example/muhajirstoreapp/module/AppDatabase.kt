// AppDatabase.kt
package com.example.muhajirstoreapp.module

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.muhajirstoreapp.model.CartDao
import com.example.muhajirstoreapp.model.CartItem
import com.example.muhajirstoreapp.model.LoggedInUser
import com.example.muhajirstoreapp.model.UserDao

@Database(entities = [CartItem::class, LoggedInUser::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun userDao(): UserDao
}