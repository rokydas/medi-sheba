package com.example.medi_sheba.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_sheba.model.appointments

@Composable
fun AllAppointmentsScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, title = "Appointments") }
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