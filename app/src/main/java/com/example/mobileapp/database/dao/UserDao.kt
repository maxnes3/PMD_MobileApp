package com.example.mobileapp.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mobileapp.database.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("select * from users")
    fun getAll():Flow<List<User>>
}