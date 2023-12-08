package com.example.mobileapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mobileapp.database.entities.Mail
import kotlinx.coroutines.flow.Flow

@Dao
interface MailDao {
    @Query("select * from mails")
    fun getAll(): Flow<List<Mail>>

    @Query("select * from mails where mails.id = :id")
    fun getById(id: Int): Flow<Mail?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(mail: Mail)

    @Update
    suspend fun update(mail: Mail)

    @Delete
    suspend fun delete(mail: Mail)
}