package com.example.mobileapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mobileapp.database.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("select * from users")
    fun getAll(): Flow<List<User>>

    @Query("select * from users where users.id = :id")
    suspend fun getById(id: Int): User?

    @Query("select * from users where users.login = :login")
    suspend fun getByLogin(login: String): User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("delete from stories")
    suspend fun deleteAll()
}