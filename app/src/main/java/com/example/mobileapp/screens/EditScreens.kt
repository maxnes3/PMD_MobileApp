package com.example.mobileapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobileapp.R
import com.example.mobileapp.components.ActiveButton
import com.example.mobileapp.components.NavigationButton
import com.example.mobileapp.components.PasswordInputField
import com.example.mobileapp.components.PlaceholderInputField
import com.example.mobileapp.database.MobileAppDataBase
import com.example.mobileapp.database.entities.Story
import com.example.mobileapp.ui.theme.ButtonColor1
import com.example.mobileapp.ui.theme.ButtonColor2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun EditStoryScreen(navController: NavHostController, storyId: Int? = null) {
    val context = LocalContext.current
    val story = remember { mutableStateOf<Story?>(null) }

    storyId?.let{
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                story.value = MobileAppDataBase.getInstance(context).storyDao().getById(storyId!!)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Image(
            painter = painterResource(id = R.drawable.editplaceholder),
            contentDescription = "editplaceholder",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(384.dp)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally))
        ActiveButton(label = "Выбрать обложку", backgroundColor = ButtonColor1, textColor = Color.Black, onClickAction = {})
        PlaceholderInputField(label = "Название", true, onTextChanged = { newName ->

        })
        PlaceholderInputField(label = "Описание", true, onTextChanged = { newDescription ->

        })
        ActiveButton(label = "Сохранить", backgroundColor = ButtonColor1, textColor = Color.Black, onClickAction = {})
        NavigationButton(navController = navController, destination = "story", label = "Назад",
            backgroundColor = ButtonColor2, textColor = Color.White)
    }
}

@Composable
fun EditMailScreen(navController: NavHostController, mailId: Int? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.mailplaceholder),
            contentDescription = "mailplaceholder",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(512.dp)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally))
        PlaceholderInputField(label = "Текс поста", false, onTextChanged = {})
        ActiveButton(label = "Сохранить", backgroundColor = ButtonColor1, textColor = Color.Black, onClickAction = {})
        NavigationButton(navController = navController, destination = "mail", label = "Назад",
            backgroundColor = ButtonColor2, textColor = Color.White)
    }
}