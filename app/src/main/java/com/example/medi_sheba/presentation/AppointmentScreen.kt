package com.example.medi_sheba.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_sheba.R
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.PrimaryColorLight

@Composable
fun AppointmentScreen(navController: NavController) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(20.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Appointment",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .background(PrimaryColorLight)
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
                .padding(vertical = 10.dp)
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .background(PrimaryColorLight)
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

        Spacer(modifier = Modifier.height(15.dp))

        val isNurseAssigned = remember { mutableStateOf(false) }

        Row {
            Text(text = "Assigned Nurse: ")
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = if (isNurseAssigned.value) "Amina Begum"
                           else "not assigned yet"
                )
                if(!isNurseAssigned.value) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = PrimaryColor, 
                            contentColor = Color.White
                        ),
                        onClick = {
                            isNurseAssigned.value = true
                        }
                    ) {
                        Text(text = "Assign a nurse")
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .background(PrimaryColorLight)
                .padding(vertical = 15.dp, horizontal = 25.dp)
        ) {
            Column {
                Text(
                    text = "Disease details:",
                    fontWeight = FontWeight.Bold
                )
                Text(text = stringResource(R.string.lorem_ipsum_small))
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .background(PrimaryColorLight)
                .padding(vertical = 15.dp, horizontal = 25.dp)
        ) {
            Column {
                Text(
                    text = "Prescription:",
                    fontWeight = FontWeight.Bold
                )
                Text(text = stringResource(R.string.prescription))
            }
        }
    }
}