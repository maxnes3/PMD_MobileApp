package com.example.mobileapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobileapp.R
import com.example.mobileapp.components.NavigationButton
import com.example.mobileapp.components.PasswordInputField
import com.example.mobileapp.components.PlaceholderInputField
import com.example.mobileapp.database.MobileAppDataBase
import com.example.mobileapp.database.entities.User
import com.example.mobileapp.ui.theme.ButtonColor1
import com.example.mobileapp.ui.theme.ButtonColor2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun Authorization(navController: NavHostController){
    val context = LocalContext.current
    val users = remember { mutableStateListOf<User>() }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            MobileAppDataBase.getInstance(context).userDao().getAll().collect { data ->
                users.clear()
                users.addAll(data)
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "login",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(512.dp)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally))
        PlaceholderInputField(label = "Логин", true, onTextChanged = {})
        PasswordInputField(label = "Пароль", onPasswordChanged = {})
        NavigationButton(navController = navController, destination = "main", label = "Вход",
            backgroundColor = ButtonColor2, textColor = Color.White)
        NavigationButton(navController = navController, destination = "registration", label = "Регистрация",
            backgroundColor = ButtonColor1, textColor = Color.Black)
    }
}