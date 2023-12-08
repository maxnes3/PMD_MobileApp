package com.example.mobileapp

import android.content.Context
import com.example.mobileapp.database.MobileAppDataBase
import com.example.mobileapp.database.repositories.MailRepository
import com.example.mobileapp.database.repositories.OfflineMailRepository
import com.example.mobileapp.database.repositories.OfflineStoryRepository
import com.example.mobileapp.database.repositories.OfflineUserRepository
import com.example.mobileapp.database.repositories.StoryRepository
import com.example.mobileapp.database.repositories.UserRepository

interface MobileAppContainer {
    val mailRepository: MailRepository
    val storyRepository: StoryRepository
    val userRepository: UserRepository
}

class MobileAppDataContainer(private val context: Context): MobileAppContainer {
    override val mailRepository: MailRepository by lazy {
        OfflineMailRepository(MobileAppDataBase.getInstance(context).mailDao())
    }

    override val storyRepository: StoryRepository by lazy {
        OfflineStoryRepository(MobileAppDataBase.getInstance(context).storyDao())
    }

    override val userRepository: UserRepository by lazy {
        OfflineUserRepository(MobileAppDataBase.getInstance(context).userDao())
    }

    companion object{
        const val TIMEOUT = 5000L
    }
}