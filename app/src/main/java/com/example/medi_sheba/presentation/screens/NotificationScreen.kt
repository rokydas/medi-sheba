package com.example.medi_sheba.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medi_sheba.model.Notification
import com.example.medi_sheba.ui.theme.PrimaryColorLight

@Composable
fun NotificationScreen(navController: NavController) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryColorLight)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Notifications",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn() {
                items(notifications) { notification ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                            .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                            .background(Color.White)
                            .padding(vertical = 15.dp, horizontal = 25.dp)
                    ) {
                        Row {
                            Column(
                                modifier = Modifier
                                    .weight(0.95f)
                            ) {
                                Text(text = notification.title)
                                Text(text = notification.description)
                            }
                            Column(
                                modifier = Modifier
                                    .weight(0.05f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ExpandMore,
                                    contentDescription = "toggle",
                                    modifier = Modifier
                                        .background(Color.White),
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

val notifications = mutableListOf<Notification>(
    Notification(
        title = "this is first title",
        description = "this is first description",
        time = "1.17 AM"
    ),
    Notification(
        title = "this is first title",
        description = "this is first description",
        time = "1.17 AM"
    ),
    Notification(
        title = "this is first title",
        description = "this is first description",
        time = "1.17 AM"
    ),
    Notification(
        title = "this is first title",
        description = "this is first description",
        time = "1.17 AM"
    ),
    Notification(
        title = "this is first title",
        description = "this is first description",
        time = "1.17 AM"
    ),
    Notification(
        title = "this is first title",
        description = "this is first description",
        time = "1.17 AM"
    ),
)
