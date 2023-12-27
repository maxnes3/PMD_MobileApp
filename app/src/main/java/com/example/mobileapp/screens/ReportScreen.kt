package com.example.mobileapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mobileapp.components.ActiveButton
import com.example.mobileapp.components.DatePicker
import com.example.mobileapp.database.viewmodels.MobileAppViewModelProvider
import com.example.mobileapp.database.viewmodels.ReportViewModel
import com.example.mobileapp.ui.theme.ButtonColor2
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ReportScreen(
    reportViewModel: ReportViewModel = viewModel(factory = MobileAppViewModelProvider.Factory)){
    val dateFormat = SimpleDateFormat("dd.MM.yyyy")

    val dateFrom = remember { mutableStateOf(Date().time) }
    val dateTo = remember { mutableStateOf(Date().time) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
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
            Text(text = "Дата с ${dateFormat.format(reportViewModel.report.value?.dateFrom?.let { Date(it) })}")
            Text(text = "Дата по ${dateFormat.format(reportViewModel.report.value?.dateTo?.let { Date(it) })}")
            Text(text = "Кол-в публикаций за период: ${reportViewModel.report.value?.postCount}")
            Text(text = "Наибольшее число публикаций у: ${reportViewModel.report.value?.mostPostAuthor}")
            Text(text = "Кол-во публикаций у пользователя:${reportViewModel.report.value?.mostPostCount}")
            ActiveButton(label = "Сбросить", backgroundColor = ButtonColor2,
                textColor = Color.White, onClickAction = {
                    reportViewModel.clearReport()
                })
        }
    }
}