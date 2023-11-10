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
fun Authorization(navController: NavHostController){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column (
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            PlaceholderInputField(label = "Логин")
            PlaceholderInputField(label = "Пароль")
            NavigationButton(navController = navController, destination = "main", label = "Вход")
            NavigationButton(navController = navController, destination = "registration", label = "Регистрация")
        }
    }
}