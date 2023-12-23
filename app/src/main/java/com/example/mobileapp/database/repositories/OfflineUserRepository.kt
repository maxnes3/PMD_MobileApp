package com.example.mobileapp.database.repositories

import com.example.mobileapp.api.model.UserRemoteSignIn
import com.example.mobileapp.database.dao.UserDao
import com.example.mobileapp.database.entities.User
import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(private val userDao: UserDao): UserRepository {
    override fun getAllUsers(): Flow<List<User>> = userDao.getAll()

    override suspend fun getUser(id: Int): User? = userDao.getById(id)

    override suspend fun getUserByLogin(user: UserRemoteSignIn): User? = userDao.getByLogin(user.login)

    override suspend fun insertUser(user: User) = userDao.insert(user)

    override suspend fun updateUser(user: User) = userDao.update(user)

    override suspend fun deleteUser(user: User) = userDao.delete(user)
}