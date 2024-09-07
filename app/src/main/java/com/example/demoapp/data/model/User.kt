package com.example.demoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

interface ICredentials {
    val username: String
    val password: String //TODO: szyfrowanie
}

interface ITokens {
    val accessToken: String
    val refreshToken: String
}

interface IUser : ICredentials {
    val email: String
    val phoneNumber: String?
    val role: UserRole
}

enum class UserRole {
    CATALOGUER
}

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Long,
    override val username: String,
    override val password: String, //TODO: szyfrowanie
    override val email: String,
    override val phoneNumber: String?,
    override val role: UserRole,

    // TODO: do keystore
    override val accessToken: String,
    override val refreshToken: String,
) : IUser, ITokens

data class LoginUser(
    override val username: String,
    override val password: String, //TODO: szyfrowanie
) : ICredentials

data class RegisterUser(
    override val username: String,
    override val password: String, //TODO: szyfrowanie
    override val email: String,
    override val phoneNumber: String?,
    override val role: UserRole = UserRole.CATALOGUER
) : IUser
