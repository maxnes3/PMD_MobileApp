package com.example.mobileapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.mobileapp.components.DataListScroll
import com.example.mobileapp.components.NavBar
import com.example.mobileapp.database.MobileAppDataBase
import com.example.mobileapp.database.entities.Story
import com.example.mobileapp.entities.StorySingleton
import com.example.mobileapp.ui.theme.BackgroundItem1
import com.example.mobileapp.ui.theme.BackgroundItem2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ListDataScreen(navController: NavHostController){

    val context = LocalContext.current
    val stories = remember { mutableStateListOf<Story>() }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            MobileAppDataBase.getInstance(context).storyDao().getAll().collect { data ->
                stories.clear()
                stories.addAll(data)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundItem1)
    ) {
        DataListScroll(navController, stories)
    }
}