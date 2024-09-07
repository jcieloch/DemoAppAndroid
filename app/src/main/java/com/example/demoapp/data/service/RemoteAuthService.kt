package com.example.demoapp.data.service
import com.example.demoapp.data.model.LoginUser
import com.example.demoapp.data.model.RegisterUser
import com.example.demoapp.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class LoginRequest(val username: String, val password: String)
data class RegisterRequest(val userName: RegisterUser)
data class TokenRequest(val refreshToken: String)
data class TokenResponse(val accessToken: String, val refreshToken: String)
data class Request(val name: String, val email: String)

interface RemoteAuthService {
    @POST("/auth/register")
    suspend fun register(@Body request: RegisterUser): Response<User>

    @POST("/auth/login")
    suspend fun login(@Body request: LoginUser): Response<User>

    @POST("refreshToken")
    suspend fun refreshToken(@Body request: TokenRequest): Response<TokenResponse>
}
