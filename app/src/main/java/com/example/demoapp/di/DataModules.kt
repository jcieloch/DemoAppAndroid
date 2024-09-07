package com.example.demoapp.di

import android.content.Context
import androidx.room.Room
import com.example.demoapp.data.local.dao.EquipmentDao
import com.example.demoapp.data.local.dao.InventoryDao
import com.example.demoapp.data.repository.UserRepository
import com.example.demoapp.data.local.dao.UserDao
import com.example.demoapp.data.local.database.AppDatabase
import com.example.demoapp.data.repository.EquipmentRepository
import com.example.demoapp.data.service.RemoteAuthService
import com.example.demoapp.data.service.RemoteEquipmentService
import com.example.demoapp.data.service.RemoteInventoryService
import com.example.demoapp.data.service.TokenProvider
import com.example.demoapp.ui.auth.AuthViewModel
import com.example.demoapp.ui.equipment.details.EquipmentDetailsViewModel
import com.example.demoapp.ui.equipment.list.EquipmentsViewModel
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModules {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app-database"
        ).build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideAuthViewModel(userRepository: UserRepository): AuthViewModel {
        return AuthViewModel(userRepository)
    }

    @Provides
    fun provideEquipmentDao(database: AppDatabase): EquipmentDao {
        return database.equipmentDao()
    }

    @Provides
    fun provideInventoryDao(database: AppDatabase): InventoryDao {
        return database.inventoryDao()
    }

    @Provides
    @Singleton
    fun provideRetrofit(tokenProvider: TokenProvider): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(AuthInterceptor(tokenProvider))
            .build()
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): RemoteAuthService {
        return retrofit.create(RemoteAuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideInventoryService(retrofit: Retrofit): RemoteInventoryService {
        return retrofit.create(RemoteInventoryService::class.java)
    }

    @Provides
    @Singleton
    fun provideEquipmentService(retrofit: Retrofit): RemoteEquipmentService {
        return retrofit.create(RemoteEquipmentService::class.java)
    }
}

class AuthInterceptor(private val tokenProvider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if(!originalRequest.url.encodedPath.startsWith("/auth")) {
            val token = tokenProvider.getAccessToken() // Pobierz Access Token z miejsca przechowywania

            // Dodaj nagłówek Authorization do żądania
            val modifiedRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            return chain.proceed(modifiedRequest)
        }
        return chain.proceed(originalRequest)
    }
}