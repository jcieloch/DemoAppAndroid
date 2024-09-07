package com.example.demoapp.ui.auth
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.demoapp.data.model.LoginUser
import com.example.demoapp.data.model.RegisterUser
import com.example.demoapp.data.repository.UserRepository
import com.example.demoapp.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _isLoggedIn = userRepository.isUserLoggedIn()
    val isLoggedIn: Flow<Boolean> get() = _isLoggedIn

    private val _user: LiveData<User?> = userRepository.getUser().asLiveData()
    val user: LiveData<User?> = _user

    suspend fun logout() {
        userRepository.logout()
    }

    suspend fun login(loginUser: LoginUser) {
            userRepository.login(loginUser)
    }

    suspend fun register(registerUser: RegisterUser) {
        userRepository.register(registerUser)
    }
}
