package com.example.mobileapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobileapp.R
import com.example.mobileapp.components.IconButton
import com.example.mobileapp.components.NavBar
import com.example.mobileapp.ui.theme.ButtonColor1
import com.example.mobileapp.ui.theme.ButtonColor2

@Composable
fun SettingsScreen(navController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.settingsplaceholder),
            contentDescription = "settings",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(384.dp)
                .padding(8.dp))
        IconButton(iconLeft = Icons.Default.AccountCircle, label = "Учётная запись",
            backgroundColor = ButtonColor2, textColor = Color.White, onClickAction = { })
        IconButton(iconLeft = Icons.Default.Face, label = "Внешний вид",
            backgroundColor = ButtonColor1, textColor = Color.Black, onClickAction = { })
        IconButton(iconLeft = Icons.Default.Share, label = "Контакты",
            backgroundColor = ButtonColor1, textColor = Color.Black, onClickAction = { })
        IconButton(iconLeft = Icons.Default.Info, label = "О приложении",
            backgroundColor = ButtonColor1, textColor = Color.Black, onClickAction = { })
        IconButton(iconLeft = Icons.Default.ExitToApp, label = "Выйти",
            backgroundColor = Color.Red, textColor = Color.White, onClickAction = { navController.navigate("") })
    }
}