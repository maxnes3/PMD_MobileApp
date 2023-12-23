package com.example.mobileapp.api.repository

import com.example.mobileapp.api.ServerService
import com.example.mobileapp.api.model.UserRemoteSignIn
import com.example.mobileapp.api.model.toUser
import com.example.mobileapp.api.model.toUserRemote
import com.example.mobileapp.database.entities.User
import com.example.mobileapp.database.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class RestUserRepository(private var service: ServerService): UserRepository {
    override fun getAllUsers(): Flow<List<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(id: Int): User = service.getUser(id).toUser()


    override suspend fun getUserByLogin(user: UserRemoteSignIn): User {
        return service.SignIn(user).toUser()
    }

    override suspend fun insertUser(user: User) {
        service.SignUp(user.toUserRemote())
    }

    override suspend fun updateUser(user: User) {
        service.updateUser(user.toUserRemote())
    }

    override suspend fun deleteUser(user: User) {
        TODO("Not yet implemented")
    }
}