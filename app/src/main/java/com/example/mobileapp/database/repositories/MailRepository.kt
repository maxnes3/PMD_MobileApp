package com.example.mobileapp.database.repositories

import com.example.mobileapp.database.entities.Mail
import kotlinx.coroutines.flow.Flow

interface MailRepository {
    fun getAllMails(): Flow<List<Mail>>

    fun getMail(id: Int):  Flow<Mail?>

    suspend fun insertMail(mail: Mail)

    suspend fun updateMail(mail: Mail)

    suspend fun deleteMail(mail: Mail)
}