package com.example.mobileapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.mobileapp.components.DataListScroll
import com.example.mobileapp.database.MobileAppDataBase
import com.example.mobileapp.database.entities.Mail
import com.example.mobileapp.ui.theme.BackgroundItem1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ListMailScreen(navController: NavHostController){

    val context = LocalContext.current
    val mails = remember { mutableStateListOf<Mail>() }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            MobileAppDataBase.getInstance(context).mailDao().getAll().collect { data ->
                mails.clear()
                mails.addAll(data)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundItem1)
    ) {
        DataListScroll(navController, mails)
    }
}