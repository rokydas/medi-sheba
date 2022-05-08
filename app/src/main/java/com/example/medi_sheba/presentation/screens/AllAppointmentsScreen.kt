package com.example.medi_sheba.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medi_sheba.model.Appoint
import com.example.medi_sheba.R
import com.example.medi_sheba.controllers.AppointmentController
import com.example.medi_sheba.controllers.ProfileController
import com.example.medi_sheba.model.*
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.background
import com.google.firebase.auth.FirebaseAuth

public const val TAG = "TAG"
val appointmentController  = AppointmentController()
@Composable
fun AllAppointmentsScreen(navController: NavController,  auth: FirebaseAuth ) {
    val profileController = ProfileController()
    var _user by rememberSaveable { mutableStateOf(User()) }
    val user = profileController.user.observeAsState()

    val userId = auth.uid
    if(user.value != null) {
        _user = user.value!!
    }
    Log.d(TAG, "user id: $userId ")

    if(userId != null) {
        profileController.getUser(userId)
    }
    Log.d(TAG, "user value: $_user ")


    //----  appointment list from Firestore

    val appointList = appointmentController.appointLists.observeAsState()
    appointmentController.getAppointment()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text =
                    "${_user.name}'s Appointments List"
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
        bottomBar = { BottomNavigationBar(navController = navController,
            title = "Appointment") }
    ) {
        Column {

            if(appointList.value != null){
                LazyColumn {
                    items(appointList.value!!) { doctor ->
//                        Log.d("Appoint", "==appointment size: ${doctor.doctor_uid} ")

//                        Image(painter = painterResource(appointment.doctorImage),
//                            contentDescription = "")

                        if(_user.userType.equals("Patients")){
//                            AppointmentDoctor(doctor, navController)
                        }else if(_user.userType.equals("Patient")){//doctor
//                            Log.d("Appoint", "doctor_uid: ${doctor.doctor_uid} ")

                            if(doctor.doctor_uid == "LZOAAm8huqUTzNhDoO8Qa07arDo1"){
                                AppointmentPatient(doctor , navController, "LZOAAm8huqUTzNhDoO8Qa07arDo1")
                            }

                        }else if(_user.userType.equals("Nurse")){
                            AppointmentNurse(doctor , navController)
                        }

                    }
                }
            }


//            if(doctors.isEmpty()) {
//                Column(
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    Text(text = "There is no appointment for you")
//                }
//            } else {
//
//            }

        }
    }
}


/*@Composable
fun AppointmentDoctor(doctor: Product, navController: NavController ) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .clickable {
                navController.navigate(ScreenItem.AppointmentScreenItem.route)
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp, vertical = 10.dp)
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(horizontal = 5.dp, vertical = 15.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
            ) {
                Image(
                    painter = painterResource(doctor.image),
                    contentDescription = "Doctor Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Text(
                        text = doctor.name,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = doctor.designation,
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray
                    )

                }
            }

        }
    }
}*/


@Composable
fun AppointmentPatient(appointment: Appoint, navController: NavController, doctor_uid: String ) {
//    val profileController = ProfileController()
//
//    var _patient by rememberSaveable { mutableStateOf(User()) }
//    val user = profileController.user.observeAsState()
//    profileController.getUser(doctor_uid)
//    _patient = user.value!!

    Log.d("task", "appoint doctor_uid-----  $doctor_uid")
    appointmentController.getUserData(LocalContext.current,
        appointment.patient_uid.toString())
    val userPatient = appointmentController.user.observeAsState()


    Box(
        modifier = Modifier
            .background(background)
            .fillMaxWidth()
//            .clickable {
//                navController.navigate(ScreenItem.AppointmentScreenItem.route)
//            }
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
                        text = "Name: ${userPatient.value?.name} ",
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


@Composable
fun AppointmentNurse(appointment: Appoint, navController: NavController ) {
    Box(
        modifier = Modifier
            .background(background)
            .fillMaxWidth()
//            .clickable {
//                navController.navigate(ScreenItem.AppointmentScreenItem.route)
//            }
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
                        text = "Patient Name",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Patient designation ",
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )

                    Text(
                        text = "Cabin No: ${appointment.cabin_no}",
                        style = MaterialTheme.typography.body1,
                        color = Color.Black,
                        fontSize = 14.sp
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

