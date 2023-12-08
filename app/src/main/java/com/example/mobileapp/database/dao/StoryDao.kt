package com.example.mobileapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mobileapp.database.entities.Story
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao {
    @Query("select * from stories")
    fun getAll(): Flow<List<Story>>

    @Query("select * from stories where stories.id = :id")
    fun getById(id: Int): Flow<Story?>

    @Query("select * from stories where stories.user_id = :userId")
    fun getByUserId(userId: Int): Flow<List<Story>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(story: Story)

    @Update
    suspend fun update(story: Story)

    @Delete
    suspend fun delete(story: Story)
}