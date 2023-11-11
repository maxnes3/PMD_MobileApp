package com.example.mobileapp.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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
            .padding(top = 8.dp, start = 16.dp, bottom = 8.dp, end = 16.dp),
        shape = RoundedCornerShape(10.dp))
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
            .padding(top = 8.dp, start = 16.dp, bottom = 8.dp, end = 16.dp),
        shape = RoundedCornerShape(10.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInputField(){
    var text = remember { mutableStateOf("") }

    OutlinedTextField(
        value = text.value,
        onValueChange = {
            text.value = it
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "",
                tint = Color.Gray
            )
        },
        placeholder = {
            Text("Поиск")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, bottom = 8.dp, end = 16.dp),
        shape = RoundedCornerShape(10.dp)
    )
}

@Composable
fun IconButton(iconLeft: ImageVector, label: String, backgroundColor: Color, textColor: Color, onClickAction: () -> Unit){
    Button(
        onClick = onClickAction,
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(64.dp)
            .padding(top = 8.dp, start = 16.dp, bottom = 8.dp, end = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Icon(
                imageVector = iconLeft,
                contentDescription = "",
                tint = textColor
            )
            Text(
                text = label,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun ActiveButton(label: String, backgroundColor: Color, textColor: Color, onClickAction: () -> Unit){
    Button(
        onClick = onClickAction,
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(64.dp)
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

@Preview(showBackground = true)
@Composable
fun PlaceholderTextFieldPreview() {
    MobileAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            PlaceholderInputField("Email")
        }
    }
}