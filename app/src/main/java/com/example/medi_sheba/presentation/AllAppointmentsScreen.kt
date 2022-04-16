package com.example.medi_sheba.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_sheba.model.Appointment
import com.example.medi_sheba.model.appointments
import com.example.medi_sheba.ui.theme.PrimaryColorLight

@Composable
fun AllAppointmentsScreen(navController: NavController) {
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