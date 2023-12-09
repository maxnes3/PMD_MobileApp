package com.example.mobileapp.database.dao

import androidx.paging.PagingSource
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
    @Query("select * from stories order by id desc")
    fun getAll(): PagingSource<Int, Story>

    @Query("select * from stories where stories.id = :id")
    fun getById(id: Int): Flow<Story?>

    @Query("select * from stories where stories.user_id = :userId order by stories.id desc")
    fun getByUserId(userId: Int): PagingSource<Int, Story>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(story: Story)

    @Update
    suspend fun update(story: Story)

    @Delete
    suspend fun delete(story: Story)
}