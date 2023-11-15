package com.example.mobileapp.components

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobileapp.R
import com.example.mobileapp.entities.Mail
import com.example.mobileapp.entities.MailSingleton
import com.example.mobileapp.entities.Story
import com.example.mobileapp.entities.StorySingleton
import com.example.mobileapp.ui.theme.BackgroundItem2
import com.example.mobileapp.ui.theme.ButtonColor1
import com.example.mobileapp.ui.theme.ButtonColor2

@Composable
fun DataListScroll(navController: NavHostController){
    val storySingleton = StorySingleton()

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ){
        item {
            addNewListItem(navController, "editstory")
        }
        items(storySingleton.getStoryList()){ item ->
            DataListItem(item = item, navController = navController)
        }
    }
}

@Composable
fun DataListItem(item: Story, navController: NavHostController){
    val isExpanded = remember {
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
                Image(painter = painterResource(id = item.cover),
                    contentDescription = item.description,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(128.dp)
                        .height(256.dp))
                Column (
                    modifier = Modifier.padding(8.dp)
                ){
                    Text(
                        text = item.title,
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ){
                    DataListItemButton("Изменить", ButtonColor2, Color.White, onClickAction = { navController.navigate("editstory") })
                    DataListItemButton("Удалить", Color.Red, Color.White, onClickAction = { })
                }
            }
        }
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
fun MailListScroll(navController: NavHostController){
    val mailSingleton = MailSingleton()

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ){
        item {
            addNewListItem(navController, "editmail")
        }
        items(mailSingleton.getMailList()){ item ->
            MailListItem(item = item)
        }
    }
}

@Composable
fun MailListItem(item: Mail){
    val isExpanded = remember {
        mutableStateOf(false)
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
                Image(painter = painterResource(id = R.drawable.post),
                    contentDescription = "message",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(96.dp)
                        .padding(8.dp))
                Column(
                    modifier = Modifier.padding(8.dp)
                ){
                    Text(
                        text = item.username,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = item.message)
                }
            }
            AnimatedVisibility(
                visible = isExpanded.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { /* Действие при нажатии кнопки */ },
                    modifier = Modifier
                        .requiredHeight(64.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonColor2
                    )
                ) {
                    Text(
                        text = "Подробнее",
                        color = Color.White,
                        fontSize = 18.sp,
                    )
                }
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
