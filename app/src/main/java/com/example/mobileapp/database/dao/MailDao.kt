package com.example.mobileapp.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mobileapp.database.entities.Mail
import kotlinx.coroutines.flow.Flow

@Dao
interface MailDao {
    @Query("select * from mails")
    fun getAll(): Flow<List<Mail>>
}