package com.example.medi_sheba.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medi_sheba.R
import com.example.medi_sheba.controllers.AppointmentController
import com.example.medi_sheba.controllers.ProfileController
import com.example.medi_sheba.model.Appointment
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.background
import com.google.firebase.auth.FirebaseAuth

val appointmentController  = AppointmentController()
@Composable
fun AllAppointmentsScreen(navController: NavController,  auth: FirebaseAuth ) {
    val profileController = ProfileController()
    val user = profileController.user.observeAsState()

    val userId = auth.uid
    if(userId != null) {
        profileController.getUser(userId)
    }

    val appointmentList = appointmentController.appointmentList.observeAsState()
    appointmentController.getAppointment()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "${user.value?.name}'s Appointments List")
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
            if(appointmentList.value != null){
                val appointments = when (user.value?.userType) {
                    "Patient" -> {
                        appointmentList.value!!.filter { appointment ->
                            appointment.patient_uid == userId
                        }
                    }
                    "Doctor" -> {
                        appointmentList.value!!.filter { appointment ->
                            appointment.doctor_uid == userId
                        }
                    }
                    "Nurse" -> {
                        appointmentList.value!!.filter { appointment ->
                            appointment.nurse_uid == userId
                        }
                    }
                    else -> {
                        appointmentList.value!!
                    }
                }

                LazyColumn {
                    items(appointments) { appointment ->
                        SingleAppointment(
                            appointment = appointment,
                            navController = navController,
                            otherPersonUid =
                                if(user.value?.userType == "Patient") {
                                    appointment.doctor_uid!!
                                } else appointment.patient_uid!!
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SingleAppointment(appointment: Appointment, navController: NavController, otherPersonUid: String ) {
    val profileController = ProfileController()
    val appointmentUser = profileController.user.observeAsState()
    profileController.getUser(appointment.doctor_uid!!)

    Box(
        modifier = Modifier
            .background(background)
            .fillMaxWidth()
            .clickable {
                navController.navigate(ScreenItem.AppointmentScreenItem.route)
            }
    ) {

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(background)
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)

            ) {
                Image(
                    painter = painterResource(R.drawable.avartar),
                    contentDescription = "profile_picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Name: ${appointmentUser.value?.name} ",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Nurse not yet assigned ",
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 10.dp)

                    )

                    Text(
                        text = "Time: ${appointment.time}",
                        style = MaterialTheme.typography.body1,
                        color = Color.Black,
                        fontSize = 14.sp
                    )


                    Surface(
                        modifier = Modifier
                            .align(Alignment.End)
                            .clip(
                                shape =
                                CircleShape.copy(all = CornerSize(5.dp))
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .background(PrimaryColor)
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                                .clip(
                                    shape = CircleShape
                                        .copy(all = CornerSize(12.dp))
                                ),
                            contentAlignment = Alignment.Center,

                            ) {
                            Text(
                                text = "Assign Nurse",
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

