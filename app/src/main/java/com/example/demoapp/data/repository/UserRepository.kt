package com.example.demoapp.data.repository

import com.example.demoapp.data.local.dao.UserDao
import com.example.demoapp.data.model.LoginUser
import com.example.demoapp.data.model.RegisterUser
import com.example.demoapp.data.model.User
import com.example.demoapp.data.service.RemoteAuthService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val authService: RemoteAuthService
) {

    fun isUserLoggedIn(): Flow<Boolean> {
        return userDao.isLoggedId()
    }

    fun getUser(): Flow<User?> {
        return userDao.getUser()
    }

    suspend fun logout(): Boolean {
        return userDao.deleteUser() > 0
    }

    suspend fun login(loginUser: LoginUser): Boolean {
        val loginResponse = authService.login(loginUser)
        if(loginResponse.isSuccessful) {
            val user = loginResponse.body()
            if (user != null) {
                userDao.deleteUser()
                userDao.insertUser(user)
            }
        }
        return true
    }

    suspend fun register(registerUser: RegisterUser) {
        val userResponse = authService.register(registerUser)
        if(userResponse.isSuccessful) {
            val user = userResponse.body()
            if (user != null) {
                userDao.deleteUser()
                userDao.insertUser(user)
            }
        }
    }
}

