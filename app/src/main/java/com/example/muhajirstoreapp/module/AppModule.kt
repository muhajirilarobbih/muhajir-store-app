package com.example.muhajirstoreapp.module// com.example.muhajirstoreapp.module.com.example.muhajirstoreapp.network.AppModule.kt
import com.example.muhajirstoreapp.repository.LoginRepository
import com.example.muhajirstoreapp.repository.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoginRepository(): LoginRepository {
        return LoginRepositoryImpl()
    }
}