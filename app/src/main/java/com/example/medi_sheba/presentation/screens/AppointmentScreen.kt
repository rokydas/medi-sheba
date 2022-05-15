package com.example.medi_sheba.presentation.screens


import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.medi_sheba.R
import com.example.medi_sheba.controllers.AppointmentController
import com.example.medi_sheba.controllers.NurseContoller
import com.example.medi_sheba.controllers.ProfileController
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.PrimaryColorLight
import com.example.medi_sheba.ui.theme.background
import com.example.medi_sheba.presentation.StaticScreen.InputField
import com.example.medi_sheba.presentation.constant.Constant.PATIENT
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.PrimaryColorLight
import com.example.medi_sheba.ui.theme.background

@Composable
fun AppointmentScreen(
    navController: NavController,
    document_id: String?,
    user_id: String?,
    user_type: String?
) {
    val isNurseAssigned = remember { mutableStateOf(false) }
    val appointmentController  = AppointmentController()
    appointmentController.getAppointDocuData(document_id.toString())
    val appointmentData = appointmentController.appoint.observeAsState()

    val profileController = ProfileController()
    val appointmentUser = profileController.user.observeAsState()
    profileController.getUser(userId = user_id.toString())

    if(appointmentData.value != null){
        isNurseAssigned.value = appointmentData.value?.doc_checked!!
    }

    val nurseProController = ProfileController()
    val nurseProfile = nurseProController.user.observeAsState()
    if(isNurseAssigned.value){
        nurseProController.getUser(userId = appointmentData.value?.nurse_uid.toString())
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
                        painter = if(user_type == "Doctor")
                            painterResource(R.drawable.avartar)
                        else painterResource(R.drawable.doctor2),
                        contentDescription = "profile_picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                    )

                    Text(text = "${appointmentUser.value?.name}")
                    if(user_type== PATIENT){
                        Text(text = "Medicine specialist")
                        Text(text = "MBBS (CMC), FCPS (CMC)")
                    }else{
                        Text(text = "Mobile No: ${appointmentUser.value?.mobileNumber}")
                        Text(text = "Address: ${appointmentUser.value?.address}")
                    }

                }
            }

            Surface(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                color = PrimaryColor,
                shape = RoundedCornerShape(topStart = 20.dp,
                    topEnd = 20.dp )) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(vertical = 20.dp, horizontal = 20.dp),
                    ) {
//                    ShowPatientDetails(isNurseAssigned = isNurseAssigned.value,
//                        nurseName = nurseProfile.value?.name.toString(),
//                    userType = user_type.toString())

                    InputPatientDetails()
                }


            }



        }
    }


}

@Composable
fun ShowPatientDetails(isNurseAssigned: Boolean,
                       nurseName: String,
                       userType: String) {

    Column {

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
                Text(text = if(isNurseAssigned)
                    stringResource(R.string.lorem_ipsum_small)
                else "disease details not assigned yet.",
                    modifier = Modifier.padding(start = 10.dp)
                )
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
                Text(text = if(isNurseAssigned)
                    stringResource(R.string.prescription)
                else "Prescription details not assigned yet.",
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        NurseBoxRow(isNurseAssigned,
            nurseName = nurseName,
            userType = userType)


        Spacer(modifier = Modifier.height(50.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = background,
                contentColor = Color.Black
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

@Composable
fun NurseBoxRow(isNurseAssigned: Boolean, nurseName: String, userType: String) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .shadow(5.dp, shape = RoundedCornerShape(10.dp))
        .background(background)
        .padding(vertical = 15.dp, horizontal = 25.dp)) {

        Text(text = "Assigned Nurse: ",
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 5.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = if(isNurseAssigned) "$nurseName"
            else "Not assigned yet",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 10.dp)

        )

//            if(!isNurseAssigned && userType == "Doctor"){
//                Spacer(modifier = Modifier.height(20.dp))
//                Button(
//                    colors = ButtonDefaults.buttonColors(
//                        backgroundColor = PrimaryColor,
//                        contentColor = Color.White
//                    ),
//                    onClick = {
////                                            isNurseAssigned.value = true
//                    },
//                    modifier = Modifier.align(Alignment.End)
//                ) {
//                    Text(text = "Assign Nurse")
//                }
//            }

    }
}



@Composable
fun InputPatientDetails() {
    val context = LocalContext.current
    val nurseContoller = NurseContoller()
    val nurseList = nurseContoller.nurseList.observeAsState()
    nurseContoller.getNurseList()

    var isLoading by rememberSaveable { mutableStateOf(false) }

    if(isLoading) {
        Dialog(
            onDismissRequest = {
                               isLoading = false
            },
            DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Box(
                contentAlignment= Alignment.Center,
                modifier = Modifier
                    .size(300.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                Column {
                    if(nurseList.value != null){
                        LazyColumn {
                            items(nurseList.value!!) { nurseUser ->
                                Text(text = "Nurse Name: ${nurseUser.name}",
                                    fontSize = 18.sp,
                                fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxSize()
        .background(background)
        .padding(vertical = 15.dp)
        .shadow(0.dp, shape = RoundedCornerShape(10.dp))
    ) {
        Column(modifier = Modifier.fillMaxWidth())  {
            val disease = rememberSaveable{ mutableStateOf("") }
            val prescription = rememberSaveable{ mutableStateOf("") }

            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                onClick = {
                    isLoading = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp, horizontal = 25.dp)
            ) {
                Text(text = "Assign Nurse")
            }



            Spacer(modifier = Modifier.height(15.dp))

            InputField(inputState = disease,
                labelId = "Disease Details",
            )

            Spacer(modifier = Modifier.height(15.dp))

            InputField(
                modifier = Modifier ,
                inputState = prescription,
                labelId = "Prescription",
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryColor,
                    contentColor = Color.White
                ),
                onClick = {
                          if(disease.value == "" && prescription.value == ""){
                              Toast.makeText(context, "Fill up all fields", Toast.LENGTH_SHORT).show()
                          }else{
                              Toast.makeText(context, "${disease.value} , prescription: ${prescription.value}", Toast.LENGTH_SHORT).show()
                          }
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {
                Text(text = "Submit")
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

