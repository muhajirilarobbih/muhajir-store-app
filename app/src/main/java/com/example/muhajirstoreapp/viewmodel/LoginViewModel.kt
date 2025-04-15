package com.example.muhajirstoreapp.viewmodel// com.example.muhajirstoreapp.viewmodel.LoginViewModel.kt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muhajirstoreapp.model.LoggedInUser
import com.example.muhajirstoreapp.network.UserRepository
import com.example.muhajirstoreapp.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var loginSuccess by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    val loggedInUser: Flow<LoggedInUser?> = userRepository.loggedInUser // Tambahkan state untuk menyimpan user

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onLoginClick(onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val success = loginRepository.login(email, password)
                if (success) {
                    // Simpan data pengguna yang login
                    val user = LoggedInUser(
                        username = email,
                        email = "$email@example.com" // Contoh email
                    )
                    userRepository.saveLoggedInUser(user)
                    onLoginSuccess()
                    loginSuccess = true
                } else {
                    errorMessage = "Login failed. Please check your credentials."
                }
            } catch (e: Exception) {
                errorMessage = "An error occurred: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
    fun logout() {
        viewModelScope.launch {
            userRepository.clearAllData() // Hapus semua data saat logout
        }
    }
}