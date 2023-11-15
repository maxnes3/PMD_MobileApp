package com.example.mobileapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobileapp.components.NavBar
import com.example.mobileapp.entities.Mail
import com.example.mobileapp.entities.MailSingleton
import com.example.mobileapp.entities.Story
import com.example.mobileapp.entities.StorySingleton
import com.example.mobileapp.ui.theme.MobileAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val mailSingleton = MailSingleton()
                    mailSingleton.addMail(Mail(0, 0, "Дзюнзи Ито", "Выложил новый"))
                    mailSingleton.addMail(Mail(1, 1, "Стивен Кинг", "Меня отменили в Твиттере"))
                    mailSingleton.addMail(Mail(0, 0, "Дзюнзи Ито", "Выложил новый"))
                    mailSingleton.addMail(Mail(1, 1, "Стивен Кинг", "Меня отменили в Твиттере"))
                    mailSingleton.addMail(Mail(0, 0, "Дзюнзи Ито", "Выложил новый"))
                    mailSingleton.addMail(Mail(1, 1, "Стивен Кинг", "Меня отменили в Твиттере"))
                    mailSingleton.addMail(Mail(0, 0, "Дзюнзи Ито", "Выложил новый"))
                    mailSingleton.addMail(Mail(1, 1, "Стивен Кинг", "Меня отменили в Твиттере"))

                    val storySingleton = StorySingleton()
                    storySingleton.addStory(Story(0, "Чужак", "Знаменитая книга стивена кинга", R.drawable.king))
                    storySingleton.addStory(Story(1, "Переулок", "История ужасов от Дзюнзи Ито", R.drawable.dzun))
                    storySingleton.addStory(Story(2, "Чужак", "Знаменитая книга стивена кинга", R.drawable.king))
                    storySingleton.addStory(Story(3, "Переулок", "История ужасов от Дзюнзи Ито", R.drawable.dzun))

                    NavBar(navController = navController)
                }
            }
        }
    }
}
