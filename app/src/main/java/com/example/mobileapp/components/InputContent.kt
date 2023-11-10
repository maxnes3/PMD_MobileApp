package com.example.mobileapp.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileapp.ui.theme.MobileAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceholderInputField(label: String){
    var text = remember { mutableStateOf("") }

    OutlinedTextField(
        value = text.value,
        onValueChange = {
            text.value = it
        },
        placeholder = {
            Text(label)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, bottom = 8.dp, end = 16.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputField(label: String){
    var text = remember { mutableStateOf("") }

    OutlinedTextField(
        value = text.value,
        onValueChange = {
            text.value = it
        },
        placeholder = {
            Text(label)
        },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, bottom = 8.dp, end = 16.dp))
}

@Preview(showBackground = true)
@Composable
fun PlaceholderTextFieldPreview() {
    MobileAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            PlaceholderInputField("Email")
        }
    }
}