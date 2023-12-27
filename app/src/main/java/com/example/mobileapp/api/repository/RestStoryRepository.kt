package com.example.mobileapp.api.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mobileapp.MobileAppContainer
import com.example.mobileapp.api.ServerService
import com.example.mobileapp.api.StoryRemoteMediator
import com.example.mobileapp.api.model.toStory
import com.example.mobileapp.api.model.toStoryRemote
import com.example.mobileapp.database.MobileAppDataBase
import com.example.mobileapp.database.entities.Story
import com.example.mobileapp.database.repositories.OfflineStoryRepository
import com.example.mobileapp.database.repositories.OfflineUserRepository
import com.example.mobileapp.database.repositories.RemoteKeysRepositoryImpl
import com.example.mobileapp.database.repositories.StoryRepository
import kotlinx.coroutines.flow.Flow

class RestStoryRepository(private var service: ServerService,
                          private val dbStoryRepository: OfflineStoryRepository,
                          private val dbUserRepository: OfflineUserRepository,
                          private val database: MobileAppDataBase,
                          private val dbRemoteKeyRepository: RemoteKeysRepositoryImpl
): StoryRepository {

    override fun getAllStories(): Flow<PagingData<Story>> {
        /*val pagingSourceFactory = {
            dbStoryRepository.getAllStoriesPagingSource()
        }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = MobileAppContainer.LIMIT,
                enablePlaceholders = false
            ),
            remoteMediator = StoryRemoteMediator(
                service,
                dbStoryRepository,
                dbUserRepository,
                database,
                dbRemoteKeyRepository,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow*/
        return Pager(
            config = PagingConfig(
                pageSize = MobileAppContainer.LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { StoryPagingSource(service) }
        ).flow
    }

    override fun getStoriesByUserId(userId: Int): Flow<PagingData<Story>> {
        val pagingSourceFactory = {
            dbStoryRepository.getUserStoriesPagingSource(userId)
        }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = MobileAppContainer.LIMIT,
                enablePlaceholders = false
            ),
            remoteMediator = StoryRemoteMediator(
                service,
                dbStoryRepository,
                dbUserRepository,
                database,
                dbRemoteKeyRepository,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
        /*return Pager(
            config = PagingConfig(
                pageSize = MobileAppContainer.LIMIT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { StoryPagingSource(service, userId) }
        ).flow*/
    }

    override suspend fun getStoryById(id: Int): Story? = service.getStory(id).toStory()

    override suspend fun insertStory(story: Story) {
        service.createStory(story.toStoryRemote())
    }

    override suspend fun updateStory(story: Story) {
        story.id?.let {
            service.updateStory(it, story.toStoryRemote())
        }
    }

    override suspend fun deleteStory(story: Story) {
        try {
            story.id?.let { this.service.deleteStory(it) }
            dbStoryRepository.deleteStory(story)
        }catch (ex: Exception){}
    }
}

class StoryPagingSource(private val service: ServerService,
                        private val userId: Int? = null) : PagingSource<Int, Story>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        try {
            val nextPageNumber = params.key ?: 1
            val pageSize = params.loadSize

            val response = if (userId != null) {
                service.getUserStories(nextPageNumber, pageSize, userId)
            } else {
                service.getStories(nextPageNumber, pageSize)
            }
            val stories = response.map { it.toStory() }

            return LoadResult.Page(
                data = stories,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (stories.isEmpty()) null else nextPageNumber + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        TODO("Not yet implemented")
    }
}