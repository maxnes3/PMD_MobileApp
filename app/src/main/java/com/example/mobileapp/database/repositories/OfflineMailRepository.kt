package com.example.mobileapp.database.repositories

import com.example.mobileapp.database.dao.MailDao
import com.example.mobileapp.database.entities.Mail
import kotlinx.coroutines.flow.Flow

class OfflineMailRepository(private val mailDao: MailDao): MailRepository {
    override fun getAllMails(): Flow<List<Mail>> = mailDao.getAll()

    override fun getMail(id: Int):  Flow<Mail?> = mailDao.getById(id)

    override suspend fun insertMail(mail: Mail) = mailDao.insert(mail)

    override suspend fun updateMail(mail: Mail) = mailDao.update(mail)

    override suspend fun deleteMail(mail: Mail) = mailDao.delete(mail)
}