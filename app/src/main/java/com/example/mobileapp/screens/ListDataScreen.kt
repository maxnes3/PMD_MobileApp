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
import androidx.navigation.NavHostController
import com.example.mobileapp.components.DataListScroll
import com.example.mobileapp.components.NavBar
import com.example.mobileapp.ui.theme.BackgroundItem1
import com.example.mobileapp.ui.theme.BackgroundItem2

@Composable
fun ListDataScreen(navController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundItem1)
    ) {
        DataListScroll(navController)
    }
}