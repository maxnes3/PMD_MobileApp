package com.example.mobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.mobileapp.components.NavBar
import com.example.mobileapp.database.entities.User
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
                    NavBar(navController = navController)
                }
            }
        }
    }
}

class GlobalUser private constructor() {
    private var user: User? = null

    fun setUser(user: User?) {
        this.user = user
    }

    fun getUser(): User? {
        return user
    }

    companion object {
        private var instance: GlobalUser? = null

        fun getInstance(): GlobalUser {
            return instance ?: synchronized(this) {
                instance ?: GlobalUser().also { instance = it }
            }
        }
    }
}
