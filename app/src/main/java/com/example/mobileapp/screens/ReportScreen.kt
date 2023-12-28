package com.example.mobileapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mobileapp.api.model.toStory
import com.example.mobileapp.api.model.toUser
import com.example.mobileapp.components.ActiveButton
import com.example.mobileapp.components.DatePicker
import com.example.mobileapp.components.MailListItem
import com.example.mobileapp.components.StoryListItem
import com.example.mobileapp.components.addNewListItem
import com.example.mobileapp.components.isListOf
import com.example.mobileapp.database.entities.Mail
import com.example.mobileapp.database.entities.Story
import com.example.mobileapp.database.viewmodels.MobileAppViewModelProvider
import com.example.mobileapp.database.viewmodels.ReportViewModel
import com.example.mobileapp.ui.theme.ButtonColor2
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ReportScreen(navController: NavHostController,
                 reportViewModel: ReportViewModel = viewModel(factory = MobileAppViewModelProvider.Factory)){
    val dateFormat = SimpleDateFormat("dd.MM.yyyy")

    val dateFrom = remember { mutableStateOf(Date().time) }
    val dateTo = remember { mutableStateOf(Date().time) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Отчёт по публикациям:",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp, start = 8.dp, end = 8.dp))
        }
        if(reportViewModel.report.value == null) {
            DatePicker(startValue = dateFrom.value, onDateSelected = { newDateFrom ->
                dateFrom.value = newDateFrom
            })
            DatePicker(startValue = dateTo.value, onDateSelected = { newDateTo ->
                dateTo.value = newDateTo
            })
            ActiveButton(label = "Сформировать", backgroundColor = ButtonColor2,
                textColor = Color.White, onClickAction = {
                    reportViewModel.createReport(dateFrom.value, dateTo.value)
                })
        }
        else{
            Text(text = "Дата с ${dateFormat.format(reportViewModel.report.value?.dateFrom?.let { Date(it) })}",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp))
            Text(text = "Дата по ${dateFormat.format(reportViewModel.report.value?.dateTo?.let { Date(it) })}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp))
            Text(text = "Кол-в публикаций за период: ${reportViewModel.report.value?.postCount}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp))
            Row(
                verticalAlignment = Alignment.Top
            ){
                Image(bitmap = reportViewModel.report.value?.mostPostAuthor?.toUser()?.photo!!.asImageBitmap(),
                    contentDescription = "message",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(96.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp)))
                Column(
                    modifier = Modifier.padding(8.dp)
                ){
                    Text(
                        text = "${reportViewModel.report.value?.mostPostAuthor?.toUser()?.login}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = "Кол-во публикаций у пользователя:${reportViewModel.report.value?.mostPostCount}")
                }
            }
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                val list = reportViewModel.report.value?.listPostAuthor!!.map {
                    it.toStory()
                }
                items(list){ item ->
                    StoryListItem(item = item, navController = navController, isReadOnly = true)
                }
            }
            /*Text(text = "Наибольшее число публикаций у: ${reportViewModel.report.value?.mostPostAuthor}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp))
            Text(text = "Кол-во публикаций у пользователя:${reportViewModel.report.value?.mostPostCount}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp))*/
        }
    }
}