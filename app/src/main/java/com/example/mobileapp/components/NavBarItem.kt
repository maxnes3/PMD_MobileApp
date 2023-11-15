package com.example.mobileapp.components

import androidx.annotation.DrawableRes

class NavBarItem(
    val route: String,
    val label: String,
    @DrawableRes
    val icon: Int
) {
}