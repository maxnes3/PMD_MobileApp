package com.example.mobileapp.database.repositories

import com.example.mobileapp.database.entities.Story
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun getAllStories(): Flow<List<Story>>

    fun getStoriesByUserId(userId: Int): Flow<List<Story>>

    fun getStoryById(id: Int):  Flow<Story?>

    suspend fun insertStory(story: Story)

    suspend fun updateStory(story: Story)

    suspend fun deleteStory(story: Story)
}