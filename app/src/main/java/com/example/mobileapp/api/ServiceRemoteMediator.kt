package com.example.mobileapp.api

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mobileapp.api.model.toStory
import com.example.mobileapp.database.MobileAppDataBase
import com.example.mobileapp.database.entities.RemoteKeyType
import com.example.mobileapp.database.entities.RemoteKeys
import com.example.mobileapp.database.entities.Story
import com.example.mobileapp.database.repositories.OfflineStoryRepository
import com.example.mobileapp.database.repositories.RemoteKeysRepositoryImpl
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ServiceRemoteMediator(private val service: ServerService,
                            private val storyRepository: OfflineStoryRepository,
                            private val database: MobileAppDataBase,
                            private val dbRemoteKeyRepository: RemoteKeysRepositoryImpl
) : RemoteMediator<Int, Story>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Story>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val stories = service.getStories(page, state.config.pageSize).map { it.toStory() }
            val endOfPaginationReached = stories.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dbRemoteKeyRepository.deleteRemoteKey(RemoteKeyType.STORY)
                    storyRepository.clearStories()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = stories.map {
                    RemoteKeys(
                        entityId = it.id!!,
                        type = RemoteKeyType.STORY,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                dbRemoteKeyRepository.createRemoteKeys(keys)
                storyRepository.insertStories(stories)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Story>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { story ->
                story.id?.let { dbRemoteKeyRepository.getAllRemoteKeys(it, RemoteKeyType.STORY) }
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Story>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { story ->
                story.id?.let { dbRemoteKeyRepository.getAllRemoteKeys(it, RemoteKeyType.STORY) }
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Story>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { storyUid ->
                dbRemoteKeyRepository.getAllRemoteKeys(storyUid, RemoteKeyType.STORY)
            }
        }
    }
}