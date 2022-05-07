package com.example.medi_sheba.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_sheba.model.appointments
import com.example.medi_sheba.ui.theme.PrimaryColor

@Composable
fun AllAppointmentsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Appointments")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                backgroundColor = PrimaryColor,
                contentColor = Color.White,
                elevation = 10.dp
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController,
            title = "Appointment") }
    ) {
        Column {
            Text(
                text = "Your Appointments",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5
            )

            Spacer(modifier = Modifier.height(50.dp))

            if(appointments.isEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "There is no appointment for you")
                }
            }
            else {
                LazyColumn {
                    items(appointments) { appointment ->
                        Image(painter = painterResource(appointment.doctorImage), contentDescription = "")
                    }
                }
            }

        }
    }
}