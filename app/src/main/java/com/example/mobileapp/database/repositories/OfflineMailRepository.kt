package com.example.mobileapp.database.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mobileapp.database.dao.MailDao
import com.example.mobileapp.database.entities.Mail
import kotlinx.coroutines.flow.Flow

class OfflineMailRepository(private val mailDao: MailDao): MailRepository {
    override fun getAllMails(): Flow<PagingData<Mail>> {
        return Pager(
            config = PagingConfig(
                pageSize = 8,
                prefetchDistance = 2,
                enablePlaceholders = true,
                initialLoadSize = 12,
                maxSize = 24
            ),
            pagingSourceFactory = {
                mailDao.getAll()
            }
        ).flow
    }

    override suspend fun getMail(id: Int):  Mail? = mailDao.getById(id)

    override suspend fun insertMail(mail: Mail) = mailDao.insert(mail)

    override suspend fun updateMail(mail: Mail) = mailDao.update(mail)

    override suspend fun deleteMail(mail: Mail) = mailDao.delete(mail)
}