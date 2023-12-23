package com.example.mobileapp.database.repositories

import androidx.paging.PagingData
import com.example.mobileapp.database.entities.Mail
import kotlinx.coroutines.flow.Flow

interface MailRepository {
    fun getAllMails(): Flow<PagingData<Mail>>

    suspend fun getMail(id: Int):  Mail?

    suspend fun insertMail(mail: Mail)

    suspend fun updateMail(mail: Mail)

    suspend fun deleteMail(mail: Mail)
}