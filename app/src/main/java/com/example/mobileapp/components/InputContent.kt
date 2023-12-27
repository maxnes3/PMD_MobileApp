package com.example.mobileapp.components

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.ui.theme.ButtonColor1
import com.example.mobileapp.ui.theme.MobileAppTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

val buttonHeightStandard = 72.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceholderInputField(label: String, startValue: String? = null, isSingleLine: Boolean, onTextChanged: (String) -> Unit){
    var text = remember { mutableStateOf("") }
    startValue?.let{
        text.value = startValue
    }

    OutlinedTextField(
        value = text.value,
        onValueChange = {
            text.value = it
            onTextChanged(it)
        },
        placeholder = {
            Text(label)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, bottom = 8.dp, end = 16.dp),
        shape = RoundedCornerShape(10.dp),
        singleLine = isSingleLine
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputField(label: String, startValue: String? = null, onPasswordChanged: (String) -> Unit){
    var text = remember { mutableStateOf("") }
    startValue?.let{
        text.value = startValue
    }

    OutlinedTextField(
        value = text.value,
        onValueChange = {
            text.value = it
            onPasswordChanged(it)
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
fun SearchInputField(onTextChanged: (String) -> Unit){
    var text = remember { mutableStateOf("") }

    OutlinedTextField(
        value = text.value,
        onValueChange = {
            text.value = it
            onTextChanged(it)
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
        shape = RoundedCornerShape(10.dp),
        singleLine = true
    )
}

@Composable
fun IconButton(iconLeft: ImageVector, label: String, backgroundColor: Color, textColor: Color, onClickAction: () -> Unit){
    Button(
        onClick = onClickAction,
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(buttonHeightStandard)
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
            .requiredHeight(buttonHeightStandard)
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

@Composable
fun DatePicker(startValue: Long? = null, onDateSelected: (Long) -> Unit) {
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val dateFormatter = remember { SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) }
    val selectedDate = remember { mutableStateOf<Long>(0) }

    startValue?.let {
        selectedDate.value = startValue
    }

    val datePickerDialog = remember { mutableStateOf(DatePickerDialog(context)) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Selected Date: ${dateFormatter.format(selectedDate.value)}")
        ActiveButton(label = "Выбрать дату", backgroundColor = ButtonColor1,
            textColor = Color.Black, onClickAction = {
                datePickerDialog.value = DatePickerDialog(
                    context,
                    { _, year: Int, month: Int, dayOfMonth: Int ->
                        val selectedDateInMillis = Calendar.getInstance().apply {
                            set(year, month, dayOfMonth)
                        }.timeInMillis

                        selectedDate.value = selectedDateInMillis
                        onDateSelected(selectedDateInMillis)
                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.value.show()
        })
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceholderTextFieldPreview() {
    MobileAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            PlaceholderInputField("Email", null,true, { })
        }
    }
}