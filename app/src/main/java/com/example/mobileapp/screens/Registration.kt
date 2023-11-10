package com.example.mobileapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobileapp.R
import com.example.mobileapp.components.NavigationButton
import com.example.mobileapp.components.PasswordInputField
import com.example.mobileapp.components.PlaceholderInputField
import com.example.mobileapp.ui.theme.ButtonColor1
import com.example.mobileapp.ui.theme.ButtonColor2

@Composable
fun Registration(navController: NavHostController){
    Column(
        modifier = Modifier.fillMaxSize().padding(bottom = 8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.registration),
            contentDescription = "registration",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(384.dp)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally))
        PlaceholderInputField(label = "Логин")
        PlaceholderInputField(label = "Email")
        PasswordInputField(label = "Пароль")
        PasswordInputField(label = "Пароль ещё раз")
        NavigationButton(navController = navController, destination = "main",
            label = "Зарегистрироваться", backgroundColor = ButtonColor2, textColor = Color.White)
        NavigationButton(navController = navController, destination = "authorization",
            label = "Назад", backgroundColor = ButtonColor1, textColor = Color.Black)
    }
}