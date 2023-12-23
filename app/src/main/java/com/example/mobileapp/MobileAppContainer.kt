package com.example.mobileapp

import android.content.Context
import com.example.mobileapp.api.ServerService
import com.example.mobileapp.api.repository.RestMailRepository
import com.example.mobileapp.api.repository.RestStoryRepository
import com.example.mobileapp.api.repository.RestUserRepository
import com.example.mobileapp.database.MobileAppDataBase
import com.example.mobileapp.database.repositories.MailRepository
import com.example.mobileapp.database.repositories.OfflineMailRepository
import com.example.mobileapp.database.repositories.OfflineStoryRepository
import com.example.mobileapp.database.repositories.OfflineUserRepository
import com.example.mobileapp.database.repositories.RemoteKeysRepositoryImpl
import com.example.mobileapp.database.repositories.StoryRepository
import com.example.mobileapp.database.repositories.UserRepository

interface MobileAppContainer {
    val mailRepository: MailRepository
    val storyRepository: StoryRepository
    val userRepository: UserRepository

    companion object{
        const val TIMEOUT = 5000L
        const val LIMIT = 10
    }
}

class MobileAppDataContainer(private val context: Context): MobileAppContainer {
    override val mailRepository: MailRepository by lazy {
        //OfflineMailRepository(MobileAppDataBase.getInstance(context).mailDao())
        RestMailRepository(ServerService.getInstance())
    }

    override val storyRepository: StoryRepository by lazy {
        //OfflineStoryRepository(MobileAppDataBase.getInstance(context).storyDao())
        RestStoryRepository(ServerService.getInstance(),
            storyReposLocal,
            userReposLocal,
            MobileAppDataBase.getInstance(context),
            remoteKeyRepository)
    }

    override val userRepository: UserRepository by lazy {
        //OfflineUserRepository(MobileAppDataBase.getInstance(context).userDao())
        RestUserRepository(ServerService.getInstance())
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