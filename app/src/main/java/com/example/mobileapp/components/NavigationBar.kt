package com.example.mobileapp.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobileapp.R
import com.example.mobileapp.screens.Authorization
import com.example.mobileapp.screens.EditMailScreen
import com.example.mobileapp.screens.EditStoryScreen
import com.example.mobileapp.screens.ListDataScreen
import com.example.mobileapp.screens.ListMailScreen
import com.example.mobileapp.screens.MainScreen
import com.example.mobileapp.screens.Registration
import com.example.mobileapp.screens.SettingsScreen

val navBarItems = listOf(
    NavBarItem(route = "main", label = "Главная", icon = R.drawable.home),
    NavBarItem(route = "story", label = "Создание", icon = R.drawable.edit),
    NavBarItem(route = "mail", label = "Уведомления", icon = R.drawable.mail),
    NavBarItem(route = "settings", label = "Настройки", icon = R.drawable.settings),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(navController: NavHostController) {
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = bottomBarState.value,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                content = {
                    NavigationBar {
                        navBarItems.forEach{item ->
                            NavigationBarItem(
                                icon = {
                                    Image(
                                        painter = painterResource(item.icon),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(bottom = 6.dp)
                                    )
                                },
                                label = {
                                    Text(
                                        text = item.label
                                    )
                                },
                                onClick = {
                                    navController.navigate(item.route)
                                },
                                selected = false,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            )
        },
        modifier = Modifier.background(Color.White)
    ) {innerPaddings ->
        NavHost(
            navController = navController,
            startDestination = "authorization",
            modifier = Modifier.padding(innerPaddings)
        ) {
            composable("authorization"){
                Authorization(navController = navController)
                bottomBarState.value = false
            }
            composable("registration"){
                Registration(navController = navController)
                bottomBarState.value = false
            }
            composable("main"){
                MainScreen(navController = navController)
                bottomBarState.value = true
            }
            composable("story"){
                ListDataScreen(navController = navController)
                bottomBarState.value = true
            }
            composable("mail"){
                ListMailScreen(navController = navController)
                bottomBarState.value = true
            }
            composable("settings"){
                SettingsScreen(navController = navController)
                bottomBarState.value = true
            }
            composable("editstory/{storyId}"){ navBackStackEntry ->
                val storyId = navBackStackEntry.arguments?.getInt("storyId")
                EditStoryScreen(navController = navController, storyId = storyId)
                bottomBarState.value = false
            }
            composable("editmail/{mailId}"){ navBackStackEntry ->
                val mailId = navBackStackEntry.arguments?.getInt("mailId")
                EditMailScreen(navController = navController, mailId = mailId)
                bottomBarState.value = false
            }
        }
    }
}

@Composable
fun NavigationButton(navController: NavHostController,
                     destination: String, label: String,
                     backgroundColor: Color, textColor: Color){
    Button(
        onClick = {
            navController.navigate(destination)
        },
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(72.dp)
            .padding(top = 8.dp, start = 16.dp, bottom = 8.dp, end = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}