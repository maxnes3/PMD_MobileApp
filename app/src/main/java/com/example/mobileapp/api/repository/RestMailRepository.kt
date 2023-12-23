package com.example.mobileapp.api.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mobileapp.MobileAppContainer
import com.example.mobileapp.api.ServerService
import com.example.mobileapp.api.model.toMail
import com.example.mobileapp.api.model.toMailRemote
import com.example.mobileapp.database.entities.Mail
import com.example.mobileapp.database.repositories.MailRepository
import kotlinx.coroutines.flow.Flow

class RestMailRepository(private var service: ServerService): MailRepository {
    override fun getAllMails(): Flow<PagingData<Mail>> {
        return Pager(
            config = PagingConfig(
                pageSize = MobileAppContainer.LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MailPagingSource(service) }
        ).flow
    }

    override suspend fun getMail(id: Int): Mail? = service.getMail(id).toMail()

    override suspend fun insertMail(mail: Mail) {
        service.createMail(mail.toMailRemote())
    }

    override suspend fun updateMail(mail: Mail) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMail(mail: Mail) {
        TODO("Not yet implemented")
    }
}

class MailPagingSource(private val service: ServerService) : PagingSource<Int, Mail>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Mail> {
        try {
            val nextPageNumber = params.key ?: 1
            val pageSize = params.loadSize

            val response = service.getMails(nextPageNumber, pageSize)
            val mails = response.map { it.toMail() } // Преобразование MailRemote в Mail

            return LoadResult.Page(
                data = mails,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (mails.isEmpty()) null else nextPageNumber + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Mail>): Int? {
        TODO("Not yet implemented")
    }
}