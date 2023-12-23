package com.example.mobileapp.database.repositories

import com.example.mobileapp.database.entities.RemoteKeyType
import com.example.mobileapp.database.entities.RemoteKeys

interface RemoteKeyRepository {
    suspend fun getAllRemoteKeys(id: Int, type: RemoteKeyType): RemoteKeys?
    suspend fun createRemoteKeys(remoteKeys: List<RemoteKeys>)
    suspend fun deleteRemoteKey(type: RemoteKeyType)
}