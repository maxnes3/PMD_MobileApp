package com.example.mobileapp.api.model

import com.example.mobileapp.database.entities.Mail
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class MailRemote(
    val id: Int? = null,
    val message: String,
    val postdate: Long? = Date().time,
    val userId: Int
)

fun MailRemote.toMail(): Mail = Mail(
    id,
    message,
    postdate,
    userId
)

fun Mail.toMailRemote():MailRemote = MailRemote(
    id,
    message,
    postdate,
    userId
)
