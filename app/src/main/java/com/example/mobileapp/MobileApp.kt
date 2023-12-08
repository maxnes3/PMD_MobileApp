package com.example.mobileapp

import android.app.Application

class MobileApp: Application() {
    lateinit var container: MobileAppContainer

    override fun onCreate() {
        super.onCreate()
        container = MobileAppDataContainer(this)
    }
}