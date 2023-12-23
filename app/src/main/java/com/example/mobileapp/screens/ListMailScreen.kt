package com.example.mobileapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.mobileapp.components.DataListScroll
import com.example.mobileapp.components.MailListItem
import com.example.mobileapp.components.StoryListItem
import com.example.mobileapp.components.addNewListItem
import com.example.mobileapp.database.MobileAppDataBase
import com.example.mobileapp.database.entities.Mail
import com.example.mobileapp.database.entities.Story
import com.example.mobileapp.database.viewmodels.MailViewModel
import com.example.mobileapp.database.viewmodels.MobileAppViewModelProvider
import com.example.mobileapp.ui.theme.BackgroundItem1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

@Composable
fun ListMailScreen(navController: NavHostController,
                   mailViewModel: MailViewModel = viewModel(
                       factory = MobileAppViewModelProvider.Factory
                   )) {
    val mails = mailViewModel.getAllMails.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundItem1)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1)
        ) {
            item {
                addNewListItem(navController, "editmail")
            }
            items(
                count = mails.itemCount,
                key = mails.itemKey { item -> item.id!! }
            ) { index: Int ->
                val mail: Mail? = mails[index]
                if (mail != null) {
                    MailListItem(item = mail, navController = navController)
                }
            }
        }
    }
}