package com.example.mobileapp.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mobileapp.database.entities.Story
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao {
    @Query("select * from stories")
    fun getAll(): Flow<List<Story>>
}