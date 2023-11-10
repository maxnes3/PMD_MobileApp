package com.example.mobileapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.mobileapp.components.DataListItem
import com.example.mobileapp.components.NavBar

@Composable
fun ListDataScreen(navController: NavHostController){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        DataListItem()
        NavBar(navController = navController)
    }
}