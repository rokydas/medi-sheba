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
import com.example.medi_sheba.presentation.constant.Constant.DOCTOR
import com.example.medi_sheba.presentation.constant.Constant.NURSE
import com.example.medi_sheba.presentation.constant.Constant.PATIENT
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
    if (userId != null) {
        profileController.getUser(userId)
    }

    val appointmentList = appointmentController.appointmentList.observeAsState()
    if (appointmentList.value == null && user.value != null) {
        when (user.value?.userType) {
            PATIENT -> {
                appointmentController.getAppointment(userId.toString(), "patient_uid")
            }
            DOCTOR -> {
                appointmentController.getAppointment(userId.toString(), "doctor_uid")

            }
            NURSE -> {
                appointmentController.getAppointment(userId.toString(), "nurse_uid")

            }
            else -> {
                appointmentController.getAppointment(userId.toString(), "")
            }


        }
    }


        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = if (user.value != null) {
                                if (user.value?.userType == PATIENT) {
                                    "${user.value?.name}'s Appointments List"
                                } else {
                                    "Patient Appointments List"
                                }
                            } else "Appointments List"
                        )
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
            bottomBar = {
                BottomNavigationBar(
                    navController = navController,
                    title = "Appointment"
                )
            }
        ) {
            Column {
                if (appointmentList.value != null) {
                    LazyColumn {
                        items(appointmentList.value!!) { appointment ->
                            SingleAppointment(
                                appointment = appointment,
                                navController = navController,
                                userType = user.value?.userType.toString(),
                                otherPersonUid =
                                if (user.value?.userType != PATIENT) {
                                    appointment.patient_uid!!
                                } else {
                                    appointment.doctor_uid!!
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SingleAppointment(
        appointment: Appointment,
        navController: NavController,
        otherPersonUid: String,
        userType: String
    ) {
        val profileController = ProfileController()
        val appointmentUser = profileController.user.observeAsState()
        profileController.getUser(otherPersonUid)



        if (appointmentUser.value != null) {
            Box(
                modifier = Modifier
                    .background(background)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(
                            ScreenItem.AppointmentScreenItem.route +
                                    "/" + appointment.document_id + "/" + otherPersonUid + "/" + userType
                        )
                    }
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()

                        .padding(horizontal = 15.dp, vertical = 10.dp)
                        .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                        .background(Color.White)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)

                    ) {
                        Image(
                            painter = if (userType.equals(PATIENT)) painterResource(R.drawable.doctor2)
                            else painterResource(R.drawable.avartar),
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
                                text = "${appointmentUser.value?.name} ",
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = when (userType) {
                                    DOCTOR -> {
                                        if (appointment.doc_checked == false)
                                            "Nurse not yet assigned "
                                        else ""

                                    }
                                    PATIENT -> {
                                        "Heart Surgeon"
                                    }
                                    NURSE -> {
                                        "Cabin No: ${appointment.cabin_no}"
                                    }
                                    else -> {
                                        ""
                                    }
                                },
                                style = MaterialTheme.typography.body1,
                                color = Color.Gray,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 10.dp),


                                )

                            Text(
                                text = "Time: ${appointment.time}",
                                style = MaterialTheme.typography.body1,
                                color = Color.Black,
                                fontSize = 14.sp
                            )


                        }
                    }
                }

            }
        }
    }





