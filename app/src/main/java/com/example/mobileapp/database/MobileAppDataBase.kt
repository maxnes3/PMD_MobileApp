package com.example.mobileapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mobileapp.database.dao.MailDao
import com.example.mobileapp.database.dao.StoryDao
import com.example.mobileapp.database.dao.UserDao
import com.example.mobileapp.database.entities.Converters
import com.example.mobileapp.database.entities.Mail
import com.example.mobileapp.database.entities.Story
import com.example.mobileapp.database.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [User::class, Story::class, Mail::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MobileAppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun storyDao(): StoryDao
    abstract fun mailDao(): MailDao

    companion object{
        private const val DB_NAME: String = "mobileApp.db"

        @Volatile
        private var INSTANCE: MobileAppDataBase? = null

        fun getInstance(appContext: Context): MobileAppDataBase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    appContext,
                    MobileAppDataBase::class.java,
                    DB_NAME
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                //populateDatabase()
                            }
                        }
                    })
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}