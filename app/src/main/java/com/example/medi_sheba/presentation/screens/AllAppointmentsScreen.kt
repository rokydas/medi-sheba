package com.example.medi_sheba.presentation.screens

import android.util.Log
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
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.medi_sheba.EncryptClass
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
    if(userId != null) {
        profileController.getUser(userId)
    }

    val appointmentList = appointmentController.appointmentList.observeAsState()
    appointmentController.getAppointment(userId, user.value?.userType)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = if(user.value != null)
                        "${EncryptClass.decrypt(user.value?.name!!)}'s Appointments List"
                    else "Appointments List")
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
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column{
                when(appointmentList.value){
                    null -> {
                        Box(modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    else ->{
                        if(appointmentList.value!!.isEmpty()) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(text = "There is no appointment")
                            }
                        }
                        else{
                            LazyColumn {
                                if (user.value != null) {
                                    items(appointmentList.value!!) { appointment ->
                                        SingleAppointment(
                                            appointment = appointment,
                                            navController = navController,
                                            otherPersonUid =
                                            if(user.value?.userType == PATIENT) {
                                                appointment.doctor_uid
                                            } else appointment.patient_uid,
                                            userType = user.value?.userType.toString()
                                        )
                                    }
                                }
                            }
                        }

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
    profileController.getUser(otherPersonUid)
    val appointmentUser = profileController.user.observeAsState()

    if(appointmentUser.value != null){
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

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(background)
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .shadow(5.dp, RoundedCornerShape(10.dp))
                    .background(Color.White)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)

                ) {
                    SubcomposeAsyncImage(
                        model = appointmentUser.value?.image,
                        contentDescription = "profile image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                    ) {
                        when (painter.state) {
                            is AsyncImagePainter.State.Loading -> {
                                CircularProgressIndicator(
                                    modifier = Modifier.padding(35.dp),
                                    color = PrimaryColor
                                )
                            }
                            is AsyncImagePainter.State.Error -> {
                                Image(
                                    painter = if (userType == PATIENT) painterResource(R.drawable.doctor2)
                                    else painterResource(R.drawable.avartar),
                                    contentDescription = "profile image"
                                )
                            }
                            else -> {
                                SubcomposeAsyncImageContent()
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "${appointmentUser.value?.name} ",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold
                        )
                        when(userType) {
                            DOCTOR -> {
                                if (!appointment.doc_checked)
                                    TextSubTitle("Nurse not yet assigned ")
                            }
                            PATIENT -> {
                                TextSubTitle("${appointmentUser.value?.doctorCategory}")

                            }
                            NURSE -> {
                                TextSubTitle(title = "Cabin No: ${appointment.cabin_no}")

                            }
                        }

                        Text(
                            text = "Time: ${appointment.time_slot}",
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

@Composable
fun TextSubTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.body1,
        color = Color.Gray,
        fontSize = 12.sp,
        modifier = Modifier.padding(start = 10.dp),
        )
}



