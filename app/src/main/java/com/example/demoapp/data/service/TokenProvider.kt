package com.example.demoapp.data.service

import android.content.Context
import android.content.SharedPreferences
import com.example.demoapp.data.local.dao.UserDao
import javax.inject.Inject

class TokenProvider @Inject constructor(
    private val userDao: UserDao
) {

    fun getAccessToken(): String? {
        return userDao.getAccessToken()
    }

}