package com.example.mobileapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.mobileapp.R
import com.example.mobileapp.components.NavBar
import com.example.mobileapp.components.SearchInputField
import com.example.mobileapp.components.StoryListItem
import com.example.mobileapp.components.addNewListItem
import com.example.mobileapp.database.entities.Story
import com.example.mobileapp.database.viewmodels.MobileAppViewModelProvider
import com.example.mobileapp.database.viewmodels.StoryViewModel

@Composable
fun MainScreen(navController: NavHostController,
               storyViewModel: StoryViewModel = viewModel(
                   factory = MobileAppViewModelProvider.Factory
               )) {
    val stories = storyViewModel.getAllStories.collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchInputField()
        if (stories.itemCount > 0){
            LazyVerticalGrid(
                columns = GridCells.Fixed(1)
            ) {
                items(
                    count = stories.itemCount,
                    key = stories.itemKey { item -> item.id!! }
                ) { index: Int ->
                    val story: Story? = stories[index]
                    if (story != null) {
                        StoryListItem(item = story, navController = navController, isReadOnly = true)
                    }
                }
            }
        }
        else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.main),
                    contentDescription = "main",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(512.dp)
                        .padding(8.dp)
                )
                Text(
                    text = "Здесь будут посты авторов",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}