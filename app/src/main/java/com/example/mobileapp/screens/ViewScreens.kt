package com.example.mobileapp.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mobileapp.R
import com.example.mobileapp.components.NavigationButton
import com.example.mobileapp.database.viewmodels.MailViewModel
import com.example.mobileapp.database.viewmodels.MobileAppViewModelProvider
import com.example.mobileapp.database.viewmodels.StoryViewModel
import com.example.mobileapp.database.viewmodels.UserViewModel
import com.example.mobileapp.ui.theme.ButtonColor2
import java.text.SimpleDateFormat
import java.util.Date

val dateFormat = SimpleDateFormat("dd.MM.yyyy")

@Composable
fun StoryViewScreen(navController: NavHostController, storyId: Int,
                    storyViewModel: StoryViewModel = viewModel(
                        factory = MobileAppViewModelProvider.Factory
                    )) {
    val context = LocalContext.current

    val cover = remember { mutableStateOf<Bitmap>(BitmapFactory.decodeResource(context.resources, R.drawable.editplaceholder)) }
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val postdate = remember { mutableStateOf<Long>(0) }

    /*val story by storyViewModel.getStoryById(storyId).collectAsState(null)
    story?.let {
        cover.value = it.cover
        title.value = it.title
        description.value = it.description
        postdate.value = it.postdate!!
    }*/

    LaunchedEffect(Unit) {
        storyId?.let {
            val story = storyViewModel.getStoryById(storyId)
            if (story != null) {
                cover.value = story.cover
                title.value = story.title
                description.value = story.description
                postdate.value = story.postdate!!
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            bitmap = cover.value.asImageBitmap(),
            contentDescription = "cover",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(512.dp)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally))
        Text(text = "Название: ${title.value}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp))
        Text(text = "Дата публикации: ${dateFormat.format(Date(postdate.value))}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp))
        Text(text = "Описание: ${description.value}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp))
        NavigationButton(navController = navController, destination = "main", label = "Назад",
            backgroundColor = ButtonColor2, textColor = Color.White)
    }
}

@Composable
fun MailViewScreen(navController: NavHostController, mailId: Int,
                   mailViewModel: MailViewModel = viewModel(
                       factory = MobileAppViewModelProvider.Factory
                   ),
                   userViewModel: UserViewModel = viewModel(
                       factory = MobileAppViewModelProvider.Factory
                   )) {
    val context = LocalContext.current

    val userName = remember { mutableStateOf("") }
    val photo = remember { mutableStateOf<Bitmap>(BitmapFactory.decodeResource(context.resources, R.drawable.photoplaceholder)) }
    val message = remember { mutableStateOf("") }
    val postdate = remember { mutableStateOf<Long>(0) }

    LaunchedEffect(Unit){
        mailViewModel.getMail(mailId).collect{
            if (it != null) {
                message.value = it.message
                postdate.value = it.postdate!!
                val user = userViewModel.getUser(it.userId)
                if (user != null) {
                    if(user.photo != null) {
                        photo.value = user.photo
                    }
                    userName.value = user.email
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            bitmap = photo.value.asImageBitmap(),
            contentDescription = "editplaceholder",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .clip(CircleShape)
                .size(384.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                .align(Alignment.CenterHorizontally))
        Text(text = "Автор: ${userName.value}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp))
        Text(text = "Дата публикации: ${dateFormat.format(Date(postdate.value))}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp))
        Text(text = "Текст: ${message.value}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp))
        NavigationButton(navController = navController, destination = "mail", label = "Назад",
            backgroundColor = ButtonColor2, textColor = Color.White)
    }
}