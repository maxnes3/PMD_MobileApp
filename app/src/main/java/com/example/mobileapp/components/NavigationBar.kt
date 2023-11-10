package com.example.mobileapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobileapp.R

@Composable
fun NavBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavItem(navController = navController, imageId = R.drawable.home, description = "homeButton", destination = "main")
        NavItem(navController = navController, imageId = R.drawable.edit, description = "editButton", destination = "listdata")
        NavItem(navController = navController, imageId = R.drawable.mail, description = "mailButton", destination = "mail")
        NavItem(navController = navController, imageId = R.drawable.settings, description = "settingsButton", destination = "")
    }
}

@Composable
fun NavItem(navController: NavHostController, imageId: Int, description: String, destination: String){
    Image(painter = painterResource(id = imageId),
        contentDescription = description,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(64.dp)
            .clickable {
                navController.navigate(destination)
            })
}

@Composable
fun NavigationButton(navController: NavHostController, destination: String, label: String){
    Button(
        onClick = {
            navController.navigate(destination)
        },
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(64.dp)
            .padding(top = 8.dp, start = 16.dp, bottom = 8.dp, end = 16.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}