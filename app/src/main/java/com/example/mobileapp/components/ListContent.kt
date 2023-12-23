package com.example.mobileapp.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.example.mobileapp.R
import com.example.mobileapp.database.MobileAppDataBase
import com.example.mobileapp.database.entities.Mail
import com.example.mobileapp.database.entities.Story
import com.example.mobileapp.database.viewmodels.MobileAppViewModelProvider
import com.example.mobileapp.database.viewmodels.StoryViewModel
import com.example.mobileapp.database.viewmodels.UserViewModel
import com.example.mobileapp.ui.theme.BackgroundItem2
import com.example.mobileapp.ui.theme.ButtonColor1
import com.example.mobileapp.ui.theme.ButtonColor2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date

val dateFormat = SimpleDateFormat("dd.MM.yyyy")

@Composable
fun <T : Any> DataListScroll(navController: NavHostController, dataList: List<T>){
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ){
        item {
            when {
                dataList.isListOf<Story>() -> addNewListItem(navController, "editstory")
                dataList.isListOf<Mail>() -> addNewListItem(navController, "editmail")
            }
        }
        items(dataList){ item ->
            when(item){
                is Story -> StoryListItem(item = item, navController = navController)
                is Mail -> MailListItem(item = item, navController = navController)
            }
        }
    }
}

inline fun <reified T> List<*>.isListOf(): Boolean {
    return isNotEmpty() && all { it is T }
}

@Composable
fun StoryListItem(item: Story, navController: NavHostController,
                  isReadOnly: Boolean? = false,
                  storyViewModel: StoryViewModel = viewModel(
                      factory = MobileAppViewModelProvider.Factory
                  )) {
    val isExpanded = remember {
        mutableStateOf(false)
    }

    val showDialog = remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, top = 10.dp, end = 18.dp, bottom = 10.dp)
            .clickable {
                isExpanded.value = !isExpanded.value
            },
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = ButtonColor1
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ){
                Image(bitmap = item.cover.asImageBitmap(),
                    contentDescription = item.description,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(128.dp)
                        .height(256.dp))
                Column (
                    modifier = Modifier.padding(8.dp)
                ){
                    Text(
                        text = "${item.title} | ${dateFormat.format(Date(item.postdate!!))}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = item.description)
                }
            }
            AnimatedVisibility(
                visible = isExpanded.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isReadOnly!!){
                    DataListItemButton(label = "Подробнее", backgroundColor = ButtonColor2,
                        textColor = Color.White, onClickAction = {
                            navController.navigate("viewstory/${item.id}")
                        })
                }
                else{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ){
                        DataListItemButton("Изменить", ButtonColor2, Color.White, onClickAction = {
                            navController.navigate("editstory/${item.id}")
                        })
                        DataListItemButton("Удалить", Color.Red, Color.White, onClickAction = {
                            showDialog.value = !showDialog.value
                        })
                    }
                }
            }
        }
    }

    if(showDialog.value) {
        DialogWindow(label = "Подтверждение",
            message = "Вы уверены что хотите удалить запись?", onConfirmAction = {
                storyViewModel.deleteStory(item)
                showDialog.value = !showDialog.value
                navController.navigate("story")
        }, onDismissAction = {
                showDialog.value = !showDialog.value
        })
    }
}

@Composable
fun DataListItemButton(label: String, backgroundColor: Color, textColor: Color, onClickAction: () -> Unit){
    Button(
        onClick = onClickAction,
        modifier = Modifier.requiredHeight(64.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        )
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 18.sp,
        )
    }
}

@Composable
fun MailListItem(item: Mail, navController: NavHostController,
                 userViewModel: UserViewModel = viewModel(
                     factory = MobileAppViewModelProvider.Factory
                 )) {
    val context = LocalContext.current
    val isExpanded = remember {
        mutableStateOf(false)
    }

    val userPhoto = remember { mutableStateOf<Bitmap>(BitmapFactory.decodeResource(context.resources, R.drawable.post)) }
    val userName = remember { mutableStateOf("UserName") }

    LaunchedEffect(Unit){
        val user = userViewModel.getUser(item.userId)
        if (user != null) {
            userName.value = user.email
            if (user.photo != null){
                userPhoto.value = user.photo
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, top = 8.dp, end = 18.dp, bottom = 8.dp)
            .clickable {
                isExpanded.value = !isExpanded.value
            },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = ButtonColor1
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ){
                Image(bitmap = userPhoto.value.asImageBitmap(),
                    contentDescription = "message",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(96.dp)
                        .padding(8.dp))
                Column(
                    modifier = Modifier.padding(8.dp)
                ){
                    Text(
                        text = "${userName.value} | ${dateFormat.format(Date(item.postdate!!))}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = item.message)
                }
            }
            AnimatedVisibility(
                visible = isExpanded.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                DataListItemButton(label = "Подробнее", backgroundColor = ButtonColor2,
                    textColor = Color.White, onClickAction = {
                        navController.navigate("viewmail/${item.id}")
                })
            }
        }
    }
}

@Composable
fun addNewListItem(navController: NavHostController, destination: String){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, top = 8.dp, end = 18.dp, bottom = 8.dp)
            .clickable {
                navController.navigate(destination)
            },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = BackgroundItem2
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(painter = painterResource(id = R.drawable.additem),
                    contentDescription = "additem",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(96.dp)
                        .padding(8.dp))
                Text(
                    text = "Добавить",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 32.dp))
            }
        }
    }
}

@Composable
fun DialogWindow(label: String, message: String, onConfirmAction: () -> Unit, onDismissAction: () -> Unit){
    AlertDialog(onDismissRequest = onDismissAction,
        title = {
            Text(text = label,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            Button(
                onClick = onConfirmAction,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor2
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "Подтвердить",
                    color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissAction,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor1
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "Отмена",
                    color = Color.Black)
            }
        }
    )
}
