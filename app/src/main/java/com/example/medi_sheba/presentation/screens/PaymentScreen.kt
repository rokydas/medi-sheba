package com.example.medi_sheba.presentation.screens

import android.app.AlarmManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medi_sheba.controllers.BookAppointmentController
import com.example.medi_sheba.model.Appointment
import com.example.medi_sheba.presentation.util.encrypt
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.background
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PaymentScreen(
    navController: NavController,
    doctorUid: String?,
    time: String?,
    serial: String?,
    date: String?,
    name: String?,
    designation: String?,
    alarmManager: AlarmManager
) {
    val auth = Firebase.auth
    val uid = auth.currentUser?.uid
    val context = LocalContext.current
    val bookAppointment = Appointment(
        doctor_uid = doctorUid!!,
        patient_uid = uid!!,
        time_slot = time!!,
        serial = serial!!,
        date = date!!
    )

    val bookAppointmentController = BookAppointmentController()
//    bookAppointmentController.bookAppointment(bookAppointment, context)
    var isOpenModal = remember { mutableStateOf(false)}

    Scaffold(
        backgroundColor = background,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Payment")
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
    ) {
        if (isOpenModal.value) {

        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp)
                    .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(vertical = 15.dp, horizontal = 25.dp)
            ) {
                Column {
                    Text(
                        text = "Doctor: $name",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Designation: $designation",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Date: $date",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Serial: $time",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Serial: $serial",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Are you sure to pay?",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                                .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
                            colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryColor),
                            onClick = {
                                bookAppointmentController.bookAppointment(
                                    Appointment(
                                        doctor_uid = doctorUid,
                                        patient_uid = uid,
                                        time_slot = encrypt(time),
                                        serial = encrypt(serial),
                                        date = date
                                ), context, navController, alarmManager)
                            },
                        ) {
                            Text(
                                text = "Pay Fees",
                                color = Color.White,
                                style = TextStyle(fontSize = 14.sp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}