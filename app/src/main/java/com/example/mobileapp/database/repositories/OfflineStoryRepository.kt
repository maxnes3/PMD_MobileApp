package com.example.mobileapp.database.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mobileapp.database.dao.StoryDao
import com.example.mobileapp.database.entities.Story
import kotlinx.coroutines.flow.Flow

class OfflineStoryRepository(private val storyDao: StoryDao): StoryRepository {
    override fun getAllStories(): Flow<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                prefetchDistance = 1,
                enablePlaceholders = true,
                initialLoadSize = 10,
                maxSize = 15
            ),
            pagingSourceFactory = {
                storyDao.getAll()
            }
        ).flow
    }

    override fun getStoriesByUserId(userId: Int): Flow<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                prefetchDistance = 1,
                enablePlaceholders = true,
                initialLoadSize = 10,
                maxSize = 15
            ),
            pagingSourceFactory = {
                storyDao.getByUserId(userId)
            }
        ).flow
    }

    override fun getStoryById(id: Int):  Flow<Story?> = storyDao.getById(id)

    override suspend fun insertStory(story: Story) = storyDao.insert(story)

    override suspend fun updateStory(story: Story) = storyDao.update(story)

    override suspend fun deleteStory(story: Story) = storyDao.delete(story)
}