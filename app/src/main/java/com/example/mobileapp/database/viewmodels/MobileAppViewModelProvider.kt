package com.example.mobileapp.database.viewmodels

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mobileapp.MobileApp

object MobileAppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            MailViewModel(app().container.mailRepository)
        }
        initializer {
            StoryViewModel(app().container.storyRepository)
        }
        initializer {
            UserViewModel(app().container.userRepository)
        }
        initializer {
            ReportViewModel(app().container.reportRepository)
        }
    }
}

fun CreationExtras.app(): MobileApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MobileApp)