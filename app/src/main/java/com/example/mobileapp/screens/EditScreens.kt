package com.example.mobileapp.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mobileapp.GlobalUser
import com.example.mobileapp.R
import com.example.mobileapp.components.ActiveButton
import com.example.mobileapp.components.NavigationButton
import com.example.mobileapp.components.PlaceholderInputField
import com.example.mobileapp.database.MobileAppDataBase
import com.example.mobileapp.database.entities.Mail
import com.example.mobileapp.database.entities.Story
import com.example.mobileapp.database.viewmodels.MailViewModel
import com.example.mobileapp.database.viewmodels.MobileAppViewModelProvider
import com.example.mobileapp.database.viewmodels.StoryViewModel
import com.example.mobileapp.ui.theme.ButtonColor1
import com.example.mobileapp.ui.theme.ButtonColor2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun EditStoryScreen(navController: NavHostController, storyId: Int? = null,
                    storyViewModel: StoryViewModel = viewModel(
                        factory = MobileAppViewModelProvider.Factory
                    )) {
    val context = LocalContext.current

    val cover = remember { mutableStateOf<Bitmap>(BitmapFactory.decodeResource(context.resources, R.drawable.editplaceholder)) }
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }

    val imageData = remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {uri: Uri? ->
            imageData.value = uri
        }
    imageData.value?.let {
        if (Build.VERSION.SDK_INT < 28) {
            cover.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver, imageData.value)
        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, imageData.value!!)
            cover.value = ImageDecoder.decodeBitmap(source)
        }
    }

    storyId?.let{
        val story by storyViewModel.getStoryById(storyId).collectAsState(null)
        cover.value = story!!.cover
        title.value = story!!.title
        description.value = story!!.description
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Image(
            bitmap = cover.value.asImageBitmap(),
            contentDescription = "editplaceholder",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(384.dp)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally))
        ActiveButton(label = "Выбрать обложку", backgroundColor = ButtonColor1, textColor = Color.Black, onClickAction = {
            launcher.launch("image/*")
        })
        PlaceholderInputField(label = "Название", isSingleLine = true,
            startValue = title.value, onTextChanged = { newName ->
            title.value = newName
        })
        PlaceholderInputField(label = "Описание", isSingleLine = true,
            startValue = description.value, onTextChanged = { newDescription ->
            description.value = newDescription
        })
        ActiveButton(label = "Сохранить", backgroundColor = ButtonColor1, textColor = Color.Black, onClickAction = {
            storyId?.let {
                storyViewModel.updateStory(
                    Story(
                        id = storyId,
                        cover = cover.value,
                        title = title.value,
                        description = description.value,
                        userId = GlobalUser.getInstance().getUser()?.id!!
                    )
                )
            } ?: run {
                storyViewModel.insertStory(
                    Story(
                        cover = cover.value,
                        title = title.value,
                        description = description.value,
                        userId = GlobalUser.getInstance().getUser()?.id!!
                    )
                )
            }
            navController.navigate("story")
        })
        NavigationButton(navController = navController, destination = "story", label = "Назад",
            backgroundColor = ButtonColor2, textColor = Color.White)
    }
}

@Composable
fun EditMailScreen(navController: NavHostController,
                   mailViewModel: MailViewModel = viewModel(
                       factory = MobileAppViewModelProvider.Factory
                   )) {
    val message = remember { mutableStateOf("") }

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
        PlaceholderInputField(label = "Текс поста", isSingleLine = false, onTextChanged = { newmessage ->
            message.value = newmessage
        })
        ActiveButton(label = "Сохранить", backgroundColor = ButtonColor1, textColor = Color.Black, onClickAction = {
            mailViewModel.insertMail(
                Mail(
                    message = message.value,
                    userId = GlobalUser.getInstance().getUser()?.id!!
                )
            )
            navController.navigate("mail")
        })
        NavigationButton(navController = navController, destination = "mail", label = "Назад",
            backgroundColor = ButtonColor2, textColor = Color.White)
    }
}

@Composable
fun EditUserScreen(navController: NavHostController){
    val context = LocalContext.current

    val photo = remember { mutableStateOf<Bitmap>(BitmapFactory.decodeResource(context.resources, R.drawable.photoplaceholder)) }
    val name = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }

    val imageData = remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {uri: Uri? ->
            imageData.value = uri
        }
    imageData.value?.let {
        if (Build.VERSION.SDK_INT < 28) {
            photo.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver, imageData.value)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, imageData.value!!)
            photo.value = ImageDecoder.decodeBitmap(source)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Image(
            bitmap = photo.value.asImageBitmap(),
            contentDescription = "editplaceholder",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(384.dp)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally))
        ActiveButton(label = "Выбрать фото", backgroundColor = ButtonColor1, textColor = Color.Black, onClickAction = {
            launcher.launch("image/*")
        })
        PlaceholderInputField(label = "Никнейм", isSingleLine = true,
            startValue = name.value, onTextChanged = { newName ->
                name.value = newName
            })
        PlaceholderInputField(label = "Пароль", isSingleLine = true,
            startValue = password.value, onTextChanged = { newPassword ->
                password.value = newPassword
            })
        PlaceholderInputField(label = "Почта", isSingleLine = true,
            startValue = email.value, onTextChanged = { newEmail ->
                email.value = newEmail
            })
        ActiveButton(label = "Сохранить", backgroundColor = ButtonColor1, textColor = Color.Black, onClickAction = {
            //edit.value = !edit.value
        })
        NavigationButton(navController = navController, destination = "story", label = "Назад",
            backgroundColor = ButtonColor2, textColor = Color.White)
    }
}