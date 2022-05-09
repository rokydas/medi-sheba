package com.example.medi_sheba.presentation.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medi_sheba.R
import com.example.medi_sheba.controllers.AppointmentController
import com.example.medi_sheba.controllers.ProfileController
import com.example.medi_sheba.model.Appointment
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.PrimaryColorLight
import com.example.medi_sheba.ui.theme.background
import com.google.gson.Gson
import java.util.ArrayList


@Composable
fun AppointmentScreen(
    navController: NavController,
    bottomNavController: NavController?,
    document_id: String?,
    user_id: String?,
    user_type: String?

) {
    val isNurseAssigned = remember { mutableStateOf(false) }
    val appointmentController  = AppointmentController()
    appointmentController.getAppointDocuData(document_id.toString())
    val appoint = appointmentController.appoint.observeAsState()

    val profileController = ProfileController()
    val appointmentUser = profileController.user.observeAsState()
    profileController.getUser(userId = user_id.toString())

    if(appoint.value != null){
        isNurseAssigned.value = appoint.value?.doc_checked!!
    }

    val nurseProController = ProfileController()
    val nurseProfile = nurseProController.user.observeAsState()
    if(isNurseAssigned.value){
        nurseProController.getUser(userId = appoint.value?.nurse_uid.toString())
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Appointment Details")
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)

        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp, horizontal = 25.dp)
            ) {

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize() ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = if(user_type == "Doctor") painterResource(R.drawable.avartar)
                        else painterResource(R.drawable.doctor2),
                        contentDescription = "profile_picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                    )

                    Text(text = "Dr. Sarose Datta")
                    Text(text = "Medicine specialist")
                    Text(text = "MBBS (CMC), FCPS (CMC)")
                }
            }

            Surface(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                color = PrimaryColor,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp )) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(vertical = 20.dp, horizontal = 20.dp)
                    ,

                    ) {
                    Column {
                        NurseBoxRow(isNurseAssigned.value,
                            nurseProfile.value?.name.toString(),
                        userType = user_type.toString())

                        Spacer(modifier = Modifier.height(15.dp))


                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                                .background(background)
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

                        Spacer(modifier = Modifier.height(15.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                                .background(background)
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
            }



        }
    }


}

@Composable
fun NurseBoxRow(isNurseAssigned: Boolean, nurseName: String, userType: String) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .shadow(5.dp, shape = RoundedCornerShape(10.dp))
        .background(background)
        .padding(vertical = 15.dp, horizontal = 25.dp))
    {
        Text(text = "Assigned Nurse: ",
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 5.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = if(isNurseAssigned) "$nurseName"
                else "Not assigned yet",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp

            )

            if(!isNurseAssigned && userType == "Doctor"){
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = PrimaryColor,
                        contentColor = Color.White
                    ),
                    onClick = {
//                                            isNurseAssigned.value = true
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Assign Nurse")
                }
            }

        }
    }
}


@Composable
fun AppointmentScreen1(
    navController: NavController,
    bottomNavController: NavController?,
    document_id: String?,
    user_id: String?

    ) {
//    Log.e("usertype", "SingleAppointment: ${document_id}", )
//    val appointmentController  = AppointmentController()
//    appointmentController.getAppointDocuData(document_id.toString())
//    val appoint = appointmentController.appoint.observeAsState()

    val profileController = ProfileController()
    val appointmentUser = profileController.user.observeAsState()
    profileController.getUser(userId = user_id.toString())

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Appointment Details")
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(20.dp)
        ) {

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
                        Text(text = "Dr. ${appointmentUser.value?.name}")
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
                    Text(text = "Doctor: ")
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(text = "Dr. ${appointmentUser.value?.name}")
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


}

