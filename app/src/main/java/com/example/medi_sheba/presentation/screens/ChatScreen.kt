package com.example.medi_sheba.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_sheba.ui.theme.PrimaryColor

@Composable
fun ChatScreen(navController: NavController? = null) {

    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 2.dp)
                    .padding(10.dp)
            ) {
                Text(text = "Showrav Das",)
            }
            MessagesUI(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            MessageField()
        }
    }
}

@Composable
fun MessageField() {
    val message = rememberSaveable { mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = message.value,
            onValueChange = {
                message.value = it
            },
            maxLines = 3,
            trailingIcon = {
                Icon(imageVector = Icons.Default.Send, contentDescription = "")
            },
            placeholder = {
                Text(
                    text = "Write your message",
                    color = Color.Gray
                )
            }
        )
    }
}

@Composable
fun MessagesUI(
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(20.dp)
    ) {
        items(messages) { message ->
            Text(text = message)
        }
    }
}

val messages = listOf<String>("Hi", "Hello", "ki obostha?")

@Preview
@Composable
fun Abc() {
    ChatScreen()
}