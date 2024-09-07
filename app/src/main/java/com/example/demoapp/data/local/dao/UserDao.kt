package com.example.demoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.demoapp.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("DELETE FROM users")
    suspend fun deleteUser(): Int

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT accessToken FROM users")
    fun getAccessToken(): String

    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): Flow<User?>

    @Query("SELECT EXISTS(SELECT 1 FROM users LIMIT 1)")
    fun isLoggedId(): Flow<Boolean>
}
