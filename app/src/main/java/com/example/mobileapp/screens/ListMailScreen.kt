package com.example.mobileapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.mobileapp.components.DataListScroll
import com.example.mobileapp.entities.MailSingleton
import com.example.mobileapp.ui.theme.BackgroundItem1

@Composable
fun ListMailScreen(navController: NavHostController){
    val mailSingleton = MailSingleton()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundItem1)
    ) {
        DataListScroll(navController, mailSingleton.getMailList())
    }
}