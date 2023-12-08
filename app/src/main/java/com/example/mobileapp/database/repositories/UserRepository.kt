package com.example.mobileapp.database.repositories

import com.example.mobileapp.database.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAllUsers(): Flow<List<User>>

    suspend fun getUser(id: Int):  User?

    suspend fun getUserByLogin(login: String): User?

    suspend fun insertUser(user: User)

    suspend fun updateUser(user: User)

    suspend fun deleteUser(user: User)
}