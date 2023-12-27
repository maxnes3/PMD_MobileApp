package com.example.mobileapp.database.viewmodels

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.api.ApiStatus
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

open class CustomViewModel : ViewModel() {
    var apiStatus by mutableStateOf(ApiStatus.NONE)
        private set

    var apiError by mutableStateOf("")
        private set

    fun runInScope(
        actionSuccess: suspend () -> Unit,
        actionError: suspend () -> Unit
    ) {
        viewModelScope.launch {
            apiStatus = ApiStatus.LOADING
            runCatching {
                actionSuccess()
                apiStatus = ApiStatus.DONE
                apiError = ""
            }.onFailure { e: Throwable ->
                when (e) {
                    is IOException,
                    is HttpException -> {
                        actionError()
                        apiStatus = ApiStatus.ERROR
                        apiError = e.localizedMessage ?: e.toString()
                    }
                    else -> throw e
                }
            }
        }
    }

    fun runInScope(actionSuccess: suspend () -> Unit) {
        runInScope(actionSuccess, actionError = {})
    }

    fun clearStatus(){
        apiStatus = ApiStatus.NONE
    }
}

@Composable
fun LoadingScreen(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = color,
            strokeWidth = 6.dp
        )
    }
}