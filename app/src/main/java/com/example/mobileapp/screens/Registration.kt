package com.example.mobileapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobileapp.components.NavigationButton
import com.example.mobileapp.components.PlaceholderInputField

@Composable
fun Registration(navController: NavHostController){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column (
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            PlaceholderInputField(label = "Логин")
            PlaceholderInputField(label = "Email")
            PlaceholderInputField(label = "Пароль")
            PlaceholderInputField(label = "Пароль ещё раз")
            NavigationButton(navController = navController, destination = "main", label = "Зарегистрироваться")
            NavigationButton(navController = navController, destination = "authorization", label = "Назад")
        }
    }
}