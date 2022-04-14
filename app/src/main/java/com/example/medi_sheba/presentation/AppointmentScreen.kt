package com.example.medi_sheba.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AppointmentScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(vertical = 15.dp, horizontal = 25.dp)
        ) {
            Row {
                Icon(imageVector = Icons.Default.MedicalServices, contentDescription = "")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Doctor: ")
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(text = "Dr. Abul Kashem")
                    Text(text = "Medicine specialist")
                    Text(text = "MBBS (CMC), FCPS (CMC)")
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(vertical = 15.dp, horizontal = 25.dp)
        ) {
            Row {
                Icon(imageVector = Icons.Default.MedicalServices, contentDescription = "")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Patient: ")
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(text = "Hannan")
                    Text(text = "Age: 50")
                    Text(text = "Gender: Male")
                }
            }
        }

        val isNurseAssigned = remember { mutableStateOf(false) }

        Row(

        ) {
            Text(text = "Assigned Nurse: ")
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = if (isNurseAssigned.value) "Amina Begum"
                           else "not assigned yet"
                )
                if(!isNurseAssigned.value) {
                    Button(onClick = {
                        isNurseAssigned.value = true
                    }) {
                        Text(text = "Assign a nurse")
                    }
                }
            }

        }
    }
}