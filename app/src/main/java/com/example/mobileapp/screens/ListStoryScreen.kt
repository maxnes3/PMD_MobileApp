package com.example.mobileapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.mobileapp.GlobalUser
import com.example.mobileapp.components.DataListScroll
import com.example.mobileapp.components.StoryListItem
import com.example.mobileapp.components.addNewListItem
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
    val stories = storyViewModel.getStoriesByUserId.collectAsLazyPagingItems()
    /*val stories = remember { mutableStateListOf<Story>() }
    LaunchedEffect(Unit){
        withContext(Dispatchers.IO) {
            storyViewModel.getStoriesByUserId(GlobalUser.getInstance().getUser()?.id!!).collect {
                stories.clear()
                stories.addAll(it)
            }
        }
    }*/

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundItem1)
    ) {
        /*LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ){
            item {
                addNewListItem(navController, "editstory")
            }
            itemsIndexed(stories){ _, item ->
                StoryListItem(item = item, navController = navController)
            }
        }*/
        LazyVerticalGrid(
            columns = GridCells.Fixed(1)
        ) {
            item {
                addNewListItem(navController, "editstory")
            }
            items(
                count = stories.itemCount,
                key = stories.itemKey { item -> item.id!! }
            ) { index: Int ->
                val story: Story? = stories[index]
                if (story != null) {
                    StoryListItem(item = story, navController = navController)
                }
            }
        }
    }
}