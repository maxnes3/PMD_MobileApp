package com.example.mobileapp.api.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRemoteSignIn(
    val login: String = "",
    val password: String = "",
)