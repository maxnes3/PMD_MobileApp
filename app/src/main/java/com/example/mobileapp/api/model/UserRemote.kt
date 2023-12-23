package com.example.mobileapp.api.model

import com.example.mobileapp.database.entities.User
import kotlinx.serialization.Serializable

@Serializable
data class UserRemote(
    val id: Int? = 0,
    val login: String,
    val password: String = "",
    val email: String = "",
    val photo: String? = null
)

fun UserRemote.toUser(): User = User(
    id,
    login,
    password,
    email,
    photo?.let { RemoteConverters.toBitmap(it) },
)

fun User.toUserRemote():UserRemote = UserRemote(
    id,
    login,
    password,
    email,
    photo?.let { RemoteConverters.fromBitmap(it) },
)
