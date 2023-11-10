package com.example.mobileapp.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.R

@Composable
fun DataListScroll(){
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
    ){
        itemsIndexed(
            listOf("")
        ){ index, item ->
            DataListItem()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DataListItem(){
    val isExpanded = remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
            .clickable {
                isExpanded.value = !isExpanded.value
            },
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ){
                Image(painter = painterResource(id = R.drawable.home),
                    contentDescription = "description",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(128.dp))
                Column (
                    modifier = Modifier.padding(8.dp)
                ){
                    Text(
                        text = "Title",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = "description")
                }
            }
            AnimatedVisibility(visible = isExpanded.value) {
                Row{
                    Button(
                        onClick = { /* Действие при нажатии кнопки */ },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Изменить")
                    }
                    Button(
                        onClick = { /* Действие при нажатии кнопки */ },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Удалить")
                    }
                }
            }
        }
    }
}
