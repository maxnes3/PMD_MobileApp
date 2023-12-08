package com.example.mobileapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mobileapp.R
import com.example.mobileapp.components.ActiveButton
import com.example.mobileapp.components.NavigationButton
import com.example.mobileapp.components.PasswordInputField
import com.example.mobileapp.components.PlaceholderInputField
import com.example.mobileapp.database.MobileAppDataBase
import com.example.mobileapp.database.entities.User
import com.example.mobileapp.database.viewmodels.MobileAppViewModelProvider
import com.example.mobileapp.database.viewmodels.UserViewModel
import com.example.mobileapp.ui.theme.ButtonColor1
import com.example.mobileapp.ui.theme.ButtonColor2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun Authorization(navController: NavHostController,
                  userViewModel: UserViewModel = viewModel(
                      factory = MobileAppViewModelProvider.Factory
                  )) {
    val users = userViewModel.getAllUsers.collectAsState(emptyList()).value

    val login = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "login",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(448.dp)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally))
        PlaceholderInputField(label = "Логин", isSingleLine = true, onTextChanged = {newlogin ->
            login.value = newlogin
        })
        PasswordInputField(label = "Пароль", onPasswordChanged = {newpassword ->
            password.value = newpassword
        })
        ActiveButton(label = "Вход", backgroundColor = ButtonColor2,
            textColor = Color.White, onClickAction = {
                userViewModel.authUser(
                    User(
                        login = login.value,
                        password = password.value,
                        email = String()
                    )
                )
                navController.navigate("main")
        })
        NavigationButton(navController = navController, destination = "registration", label = "Регистрация",
            backgroundColor = ButtonColor1, textColor = Color.Black)
    }
}