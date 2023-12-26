package com.example.mobileapp

import android.content.Context
import com.example.mobileapp.api.ServerService
import com.example.mobileapp.api.repository.RestMailRepository
import com.example.mobileapp.api.repository.RestReportRepository
import com.example.mobileapp.api.repository.RestStoryRepository
import com.example.mobileapp.api.repository.RestUserRepository
import com.example.mobileapp.database.MobileAppDataBase
import com.example.mobileapp.database.repositories.MailRepository
import com.example.mobileapp.database.repositories.OfflineMailRepository
import com.example.mobileapp.database.repositories.OfflineStoryRepository
import com.example.mobileapp.database.repositories.OfflineUserRepository
import com.example.mobileapp.database.repositories.RemoteKeysRepositoryImpl
import com.example.mobileapp.database.repositories.ReportRepository
import com.example.mobileapp.database.repositories.StoryRepository
import com.example.mobileapp.database.repositories.UserRepository

interface MobileAppContainer {
    val mailRepository: MailRepository
    val storyRepository: StoryRepository
    val userRepository: UserRepository
    val reportRepository: ReportRepository

    companion object{
        const val TIMEOUT = 5000L
        const val LIMIT = 10
    }
}

class MobileAppDataContainer(private val context: Context): MobileAppContainer {
    override val mailRepository: MailRepository by lazy {
        RestMailRepository(ServerService.getInstance())
    }

    override val storyRepository: StoryRepository by lazy {
        RestStoryRepository(ServerService.getInstance(),
            storyReposLocal,
            userReposLocal,
            MobileAppDataBase.getInstance(context),
            remoteKeyRepository)
    }

    override val userRepository: UserRepository by lazy {
        RestUserRepository(ServerService.getInstance())
    }

    override val reportRepository: ReportRepository by lazy {
        RestReportRepository(ServerService.getInstance())
    }

    private val remoteKeyRepository: RemoteKeysRepositoryImpl by lazy{
        RemoteKeysRepositoryImpl(MobileAppDataBase.getInstance(context).remoteKeysDao())
    }

    private val storyReposLocal: OfflineStoryRepository by lazy {
        OfflineStoryRepository(MobileAppDataBase.getInstance(context).storyDao())
    }

    private val userReposLocal: OfflineUserRepository by lazy {
        OfflineUserRepository(MobileAppDataBase.getInstance(context).userDao())
    }
}