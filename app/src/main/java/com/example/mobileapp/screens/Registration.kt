package com.example.mobileapp.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
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
import com.example.mobileapp.GlobalUser
import com.example.mobileapp.R
import com.example.mobileapp.api.ApiStatus
import com.example.mobileapp.components.ActiveButton
import com.example.mobileapp.components.NavigationButton
import com.example.mobileapp.components.PasswordInputField
import com.example.mobileapp.components.PlaceholderInputField
import com.example.mobileapp.database.entities.User
import com.example.mobileapp.database.viewmodels.LoadingScreen
import com.example.mobileapp.database.viewmodels.MobileAppViewModelProvider
import com.example.mobileapp.database.viewmodels.UserViewModel
import com.example.mobileapp.ui.theme.ButtonColor1
import com.example.mobileapp.ui.theme.ButtonColor2

@Composable
fun Registration(navController: NavHostController,
                 userViewModel: UserViewModel = viewModel(
                     factory = MobileAppViewModelProvider.Factory
                 )) {
    val context = LocalContext.current
    when(userViewModel.apiStatus){
        ApiStatus.DONE -> {
            navController.navigate("main")
            userViewModel.clearStatus()
        }
        ApiStatus.LOADING -> LoadingScreen(ButtonColor2)
        ApiStatus.ERROR -> Toast.makeText(context, "Не удалось создать пользователя: " + userViewModel.apiError, Toast.LENGTH_SHORT).show()
        else -> {}
    }

    val login = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val repeatepassword = remember { mutableStateOf("") }

    if(userViewModel.apiStatus != ApiStatus.LOADING) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 8.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                painter = painterResource(id = R.drawable.registration),
                contentDescription = "registration",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(320.dp)
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            )
            PlaceholderInputField(
                label = "Логин",
                isSingleLine = true,
                onTextChanged = { newlogin ->
                    login.value = newlogin
                })
            PlaceholderInputField(
                label = "Email",
                isSingleLine = true,
                onTextChanged = { newemail ->
                    email.value = newemail
                })
            PasswordInputField(label = "Пароль", onPasswordChanged = { newpassword ->
                password.value = newpassword
            })
            PasswordInputField(label = "Пароль ещё раз", onPasswordChanged = { newpassword ->
                repeatepassword.value = newpassword
            })
            ActiveButton(label = "Зарегистрироваться", backgroundColor = ButtonColor2,
                textColor = Color.White, onClickAction = {
                    if (password.value == repeatepassword.value) {
                        userViewModel.regUser(
                            User(
                                login = login.value,
                                password = password.value,
                                email = email.value
                            )
                        )
                    }
                })
            NavigationButton(
                navController = navController, destination = "authorization",
                label = "Назад", backgroundColor = ButtonColor1, textColor = Color.Black
            )
        }
    }
}