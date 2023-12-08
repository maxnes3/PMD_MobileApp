package com.example.mobileapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mobileapp.GlobalUser
import com.example.mobileapp.components.DataListScroll
import com.example.mobileapp.database.MobileAppDataBase
import com.example.mobileapp.database.entities.Story
import com.example.mobileapp.database.viewmodels.MobileAppViewModelProvider
import com.example.mobileapp.database.viewmodels.StoryViewModel
import com.example.mobileapp.ui.theme.BackgroundItem1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ListStoryScreen(navController: NavHostController,
                    storyViewModel: StoryViewModel = viewModel(
                        factory = MobileAppViewModelProvider.Factory
                    )) {
    //val stories = storyViewModel.getStoriesByUserId(GlobalUser.getInstance().getUser()?.id!!).collectAsState(emptyList()).value
    val stories = storyViewModel.getAllStories.collectAsState(emptyList()).value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundItem1)
    ) {
        DataListScroll(navController, stories)
    }
}