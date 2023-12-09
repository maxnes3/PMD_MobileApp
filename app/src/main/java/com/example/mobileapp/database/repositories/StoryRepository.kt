package com.example.mobileapp.database.repositories

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.mobileapp.database.entities.Story
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun getAllStories(): Flow<PagingData<Story>>

    fun getStoriesByUserId(userId: Int): Flow<PagingData<Story>>

    fun getStoryById(id: Int):  Flow<Story?>

    suspend fun insertStory(story: Story)

    suspend fun updateStory(story: Story)

    suspend fun deleteStory(story: Story)
}