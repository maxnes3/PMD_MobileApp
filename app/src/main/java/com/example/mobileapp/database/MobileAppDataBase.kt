package com.example.mobileapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobileapp.database.entities.Mail
import com.example.mobileapp.database.entities.Story
import com.example.mobileapp.database.entities.User

@Database(entities = [User::class, Story::class, Mail::class], version = 1, exportSchema = false)
abstract class MobileAppDataBase : RoomDatabase() {
}