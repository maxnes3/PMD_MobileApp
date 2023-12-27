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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mobileapp.R
import com.example.mobileapp.screens.Authorization
import com.example.mobileapp.screens.EditMailScreen
import com.example.mobileapp.screens.EditStoryScreen
import com.example.mobileapp.screens.EditUserScreen
import com.example.mobileapp.screens.ListMailScreen
import com.example.mobileapp.screens.ListStoryScreen
import com.example.mobileapp.screens.MailViewScreen
import com.example.mobileapp.screens.MainScreen
import com.example.mobileapp.screens.Registration
import com.example.mobileapp.screens.ReportScreen
import com.example.mobileapp.screens.SettingsScreen
import com.example.mobileapp.screens.StoryViewScreen

val navBarItems = listOf(
    NavBarItem(route = "main", label = "Главная", icon = R.drawable.home),
    NavBarItem(route = "story", label = "Создание", icon = R.drawable.edit),
    NavBarItem(route = "mail", label = "Почта", icon = R.drawable.mail),
    NavBarItem(route = "report", label = "Отчёт", icon = R.drawable.report),
    NavBarItem(route = "settings", label = "Настройки", icon = R.drawable.settings)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(navController: NavHostController) {
    val topBarState = rememberSaveable { (mutableStateOf(false)) }
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = topBarState.value,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),

                content = {
                    TopAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        title = {
                            Text(
                                text = "Storyteller!",
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.roboto_regular, FontWeight.Bold
                                    )
                                ),
                                color = Color.Black
                            )
                        }
                    )
                }
            )
        },
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
                topBarState.value = true
                bottomBarState.value = false
                Authorization(navController = navController)
            }
            composable("registration"){
                topBarState.value = true
                bottomBarState.value = false
                Registration(navController = navController)
            }
            composable("main"){
                topBarState.value = false
                bottomBarState.value = true
                MainScreen(navController = navController)
            }
            composable("story"){
                topBarState.value = false
                bottomBarState.value = true
                ListStoryScreen(navController = navController)
            }
            composable("mail"){
                topBarState.value = false
                bottomBarState.value = true
                ListMailScreen(navController = navController)
            }
            composable("settings"){
                topBarState.value = true
                bottomBarState.value = true
                SettingsScreen(navController = navController)
            }
            composable("editstory"){ // Без аргумента
                topBarState.value = false
                bottomBarState.value = false
                EditStoryScreen(navController = navController)
            }
            composable(
                "editstory/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType }) //С аргументом
            ) { backStackEntry ->
                backStackEntry.arguments?.let {
                    topBarState.value = false
                    bottomBarState.value = false
                    EditStoryScreen(navController = navController, storyId = it.getInt("id"))
                }
            }
            composable("editmail"){ // Без аргумента
                topBarState.value = false
                bottomBarState.value = false
                EditMailScreen(navController = navController)
            }
            composable("edituser"){
                topBarState.value = false
                bottomBarState.value = false
                EditUserScreen(navController = navController)
            }
            composable(
                "viewstory/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType }) //С аргументом
            ) { backStackEntry ->
                backStackEntry.arguments?.let {
                    topBarState.value = false
                    bottomBarState.value = false
                    StoryViewScreen(navController = navController, storyId = it.getInt("id"))
                }
            }
            composable(
                "viewmail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType }) //С аргументом
            ) { backStackEntry ->
                backStackEntry.arguments?.let {
                    topBarState.value = false
                    bottomBarState.value = false
                    MailViewScreen(navController = navController, mailId = it.getInt("id"))
                }
            }
            composable("report"){
                topBarState.value = false
                bottomBarState.value = true
                ReportScreen()
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