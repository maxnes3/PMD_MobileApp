package com.example.mobileapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.mobileapp.components.MailListScroll
import com.example.mobileapp.components.NavBar
import com.example.mobileapp.ui.theme.BackgroundItem1

@Composable
fun ListMailScreen(navController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundItem1)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        ){
            MailListScroll()
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            NavBar(navController = navController, itemColorFilter = Color.White)
        }
    }
}