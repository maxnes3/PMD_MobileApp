package com.example.mobileapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mobileapp.database.viewmodels.MobileAppViewModelProvider
import com.example.mobileapp.database.viewmodels.ReportViewModel

@Composable
fun ReportScreen(navController: NavController,
    reportViewModel: ReportViewModel = viewModel(factory = MobileAppViewModelProvider.Factory)){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

    }
}