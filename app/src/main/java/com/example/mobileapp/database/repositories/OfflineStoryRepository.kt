package com.example.mobileapp.database.repositories

import com.example.mobileapp.database.dao.StoryDao
import com.example.mobileapp.database.entities.Story
import kotlinx.coroutines.flow.Flow

class OfflineStoryRepository(private val storyDao: StoryDao): StoryRepository {
    override fun getAllStories(): Flow<List<Story>> = storyDao.getAll()

    override fun getStoriesByUserId(userId: Int): Flow<List<Story>> = storyDao.getByUserId(userId)

    override fun getStoryById(id: Int):  Flow<Story?> = storyDao.getById(id)

    override suspend fun insertStory(story: Story) = storyDao.insert(story)

    override suspend fun updateStory(story: Story) = storyDao.update(story)

    override suspend fun deleteStory(story: Story) = storyDao.delete(story)
}