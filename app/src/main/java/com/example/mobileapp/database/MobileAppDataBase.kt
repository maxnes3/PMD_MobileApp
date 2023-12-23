package com.example.mobileapp.database

import android.content.Context
import android.graphics.BitmapFactory
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mobileapp.R
import com.example.mobileapp.database.dao.MailDao
import com.example.mobileapp.database.dao.RemoteKeysDao
import com.example.mobileapp.database.dao.StoryDao
import com.example.mobileapp.database.dao.UserDao
import com.example.mobileapp.database.entities.Converters
import com.example.mobileapp.database.entities.Mail
import com.example.mobileapp.database.entities.RemoteKeys
import com.example.mobileapp.database.entities.Story
import com.example.mobileapp.database.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [User::class, Story::class, Mail::class, RemoteKeys::class], version = 10, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MobileAppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun storyDao(): StoryDao
    abstract fun mailDao(): MailDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object{
        private const val DB_NAME: String = "my-db"

        @Volatile
        private var INSTANCE: MobileAppDataBase? = null

        suspend fun initialDataBase(appContext: Context){
            /*INSTANCE?.let { database ->
                val userDao = database.userDao()
                userDao.insert(User(id = 1, login = "Дзюнзи Ито", password = "1234", email = "ito@gmail.com"))
                userDao.insert(User(id = 2, login = "Стивен Кинг", password = "4321", email = "king@gmail.com"))

                val storyDao = database.storyDao()
                storyDao.insert(Story(title = "Переулок", description = "История ужасов от Дзюнзи Ито",
                    cover = BitmapFactory.decodeResource(appContext.resources, R.drawable.dzun), userId = 1))
                storyDao.insert(Story(title = "Чужак", description = "Знаменитая книга стивена кинга",
                    cover = BitmapFactory.decodeResource(appContext.resources, R.drawable.king), userId = 2))

                val mailDao = database.mailDao()
                for (i in 0..50){
                    if (i % 2 == 0){
                        mailDao.insert(Mail(message = "Выложил новые страницы", userId = 1))
                    }
                    else{
                        mailDao.insert(Mail(message = "Меня отменили в Твиттере", userId = 2))
                    }
                }
            }*/
        }

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
                                initialDataBase(appContext)
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}