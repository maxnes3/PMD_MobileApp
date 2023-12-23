package com.example.mobileapp.database.repositories

import com.example.mobileapp.database.dao.RemoteKeysDao
import com.example.mobileapp.database.entities.RemoteKeyType
import com.example.mobileapp.database.entities.RemoteKeys

class RemoteKeysRepositoryImpl(private val remoteKeysDao: RemoteKeysDao): RemoteKeyRepository {
    override suspend fun getAllRemoteKeys(id: Int, type: RemoteKeyType) =
        remoteKeysDao.getRemoteKeys(id, type)

    override suspend fun createRemoteKeys(remoteKeys: List<RemoteKeys>) =
        remoteKeysDao.insertAll(remoteKeys)

    override suspend fun deleteRemoteKey(type: RemoteKeyType) =
        remoteKeysDao.clearRemoteKeys(type)
}