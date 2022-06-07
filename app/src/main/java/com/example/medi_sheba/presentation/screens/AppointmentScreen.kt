package com.example.medi_sheba.presentation.screens


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.medi_sheba.R
import com.example.medi_sheba.controllers.AppointmentController
import com.example.medi_sheba.controllers.NurseContoller
import com.example.medi_sheba.controllers.ProfileController
import com.example.medi_sheba.model.Appointment
import com.example.medi_sheba.model.User
import com.example.medi_sheba.presentation.BarChart.BarChartContent
import com.example.medi_sheba.presentation.StaticScreen.InputField
import com.example.medi_sheba.presentation.constant.Constant.DOCTOR
import com.example.medi_sheba.presentation.constant.Constant.PATIENT
import com.example.medi_sheba.presentation.prescription.generatePDF
import com.example.medi_sheba.presentation.prescription.getDirectory
import com.example.medi_sheba.presentation.prescription.requestForegroundPermission
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.background
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun AppointmentScreen(
    navController: NavController,
    document_id: String?,
    user_id: String?,
    userType: String?
) {
    val isNurseAssigned = remember { mutableStateOf(false) }
    val isCheckPatient = remember { mutableStateOf(false) }
    val appointmentController  = AppointmentController()
    appointmentController.getAppointDocuData(document_id.toString())
    val appointmentData = appointmentController.appoint.observeAsState()


    val profileController = ProfileController()
    val appointmentUser = profileController.user.observeAsState()
    profileController.getUser(userId = user_id.toString())

    if(appointmentData.value != null && appointmentData.value?.nurse_uid != ""){
            isNurseAssigned.value = true
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

        if(appointmentUser.value != null){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PrimaryColor)
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

                        Text(text = "${appointmentUser.value?.name}", color = Color.White,
                            modifier = Modifier.clickable {
                                navController.navigate(ScreenItem.PrescriptScreenItem.route)
                            })
                        if(userType == PATIENT){
                            Text(text = "${appointmentUser.value?.doctorCategory}", color = Color.White)
                            Text(text = "${appointmentUser.value?.doctorDesignation}", color = Color.White)
                        }else{
                            Text(text = "Mobile No: ${appointmentUser.value?.mobileNumber}", color = Color.White)
                            Text(text = "Address: ${appointmentUser.value?.address}", color = Color.White)
                        }

                    }
                }

                Surface(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                    color = background,
                    shape = RoundedCornerShape(topStart = 20.dp,
                        topEnd = 20.dp )) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(vertical = 20.dp, horizontal = 20.dp),
                    ) {

                        if(isCheckPatient.value){
                            appointmentController.getAppointDocuData(document_id.toString())
                            InputPatientDetails(document_id, appointmentData.value, isCheckPatient)
                        }else{
                            appointmentController.getAppointDocuData(document_id.toString())

                            if(appointmentData.value != null && appointmentUser.value != null){
                                ShowPatientDetails(
                                    user_id = user_id,
                                    isNurseAssigned = isNurseAssigned.value,
                                    nurseName = nurseProfile.value?.name.toString(),
                                    userType = userType.toString(),
                                    isCheckPatient = isCheckPatient,
                                    appointment = appointmentData.value,
                                    doctorDetails = appointmentUser.value
                                )
                            }

                        }



                    }


                }



            }
        }else{
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }


}

@Composable
fun ShowPatientDetails(
    user_id: String?,
    isNurseAssigned: Boolean,
    nurseName: String,
    userType: String,
    isCheckPatient: MutableState<Boolean>,
    appointment: Appointment?,
    doctorDetails: User?,

    ) {
    val context = LocalContext.current
    val patientController = ProfileController()
    val patientData = patientController.user.observeAsState()
    val ownUID = FirebaseAuth.getInstance().uid
    patientController.getUser(userId = ownUID.toString())

    Column(modifier = Modifier.padding(vertical = 20.dp)) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(vertical = 15.dp, horizontal = 25.dp)
        ) {
            Column {
                Text(
                    text = "Disease details:",
                    fontWeight = FontWeight.Bold
                )
                Text(text = if(appointment?.disease_details !="" && appointment?.disease_details != null)
                    "${appointment?.disease_details}"
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
                .background(Color.White)
                .padding(vertical = 15.dp, horizontal = 25.dp)
        ) {
            Column {
                Text(
                    text = "Prescription:",
                    fontWeight = FontWeight.Bold
                )
                Text(text = if(appointment?.prescription != "" && appointment?.prescription != null)
                    "${appointment?.prescription}"
                else "Prescription details not assigned yet.",
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        NurseBoxRow(isNurseAssigned,
            nurseName = nurseName,
            userType = userType)


        Spacer(modifier = Modifier.height(15.dp))

        //barchart
        if(isCheckPatient.value){
            val FB_UID = FirebaseAuth.getInstance().uid
            when (userType) {
                DOCTOR -> {
                    BarChartContent(patient_uid = user_id.toString(), doctor_uid = FB_UID.toString())
                }
                PATIENT -> {
                    BarChartContent(patient_uid = FB_UID.toString(), doctor_uid = user_id.toString())
                }
                else -> {
                    BarChartContent(patient_uid = user_id.toString(), doctor_uid = appointment?.doctor_uid.toString() )
                }
            }
        }



        Spacer(modifier = Modifier.height(30.dp))

        if(userType == DOCTOR){
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryColor,
                    contentColor = Color.White
                ),
                onClick = {
                    isCheckPatient.value = true
                },
                modifier = Modifier.align(Alignment.End)) {
                Text(text = "Check Patient")
            }
        }

        if(userType == PATIENT && appointment!!.doc_checked  ){
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryColor,
                    contentColor = Color.White
                ),
                onClick = {
                    requestForegroundPermission(context)
                    if(patientData.value != null){
                        val data_map: HashMap<String, String> = HashMap()
                        data_map["doctor_name"] = doctorDetails!!.name
                        data_map["doctor_designation"] = doctorDetails.doctorDesignation
                        data_map["doctor_category"] =  doctorDetails.doctorCategory
                        data_map["doctor_phn"] =  doctorDetails.mobileNumber
                        data_map["doctor_address"] =  doctorDetails.address
                        data_map["name"] = patientData.value!!.name
                        data_map["phn"] = patientData.value!!.mobileNumber
                        data_map["address"] = patientData.value!!.address
                        data_map["weight"] = appointment.weight
                        data_map["nurse_name"] = nurseName
                        data_map["disease"] = appointment.disease_details
                        data_map["prescript"] = appointment.prescription

                        generatePDF(context, getDirectory(context),  data_map)
                    }


                },
                modifier = Modifier.align(Alignment.End)) {
                Text(text = "Download Prescription")
            }
        }

    }

}

@Composable
fun NurseBoxRow(isNurseAssigned: Boolean, nurseName: String, userType: String) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .shadow(5.dp, shape = RoundedCornerShape(10.dp))
        .background(Color.White)
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
    }
}



@Composable
fun InputPatientDetails(document_id: String?,
                        appointment: Appointment?,
                        isCheckPatient: MutableState<Boolean>) {
    val context = LocalContext.current
    val nurseContoller = NurseContoller()
    val nurseList = nurseContoller.nurseList.observeAsState()
    nurseContoller.getNurseList()

    val nurseProController = ProfileController()
    val nurseProfile = nurseProController.user.observeAsState()
    if(appointment?.nurse_uid != ""){
        Log.d("nurse", "InputPatientDetails: ${appointment?.nurse_uid}")
        nurseProController.getUser(userId = appointment?.nurse_uid.toString())

    }
    Log.d("nurse", "InputPatientDetails: ${nurseProfile.value?.name}")



    var isDialogShow by rememberSaveable { mutableStateOf(false) }
    val selectedNurseName = rememberSaveable{ mutableStateOf("") }
    val selectedNurseUid = rememberSaveable{ mutableStateOf("") }


    val disease = rememberSaveable{ mutableStateOf("") }
    val prescription = rememberSaveable{ mutableStateOf("") }
    val cabin = rememberSaveable{ mutableStateOf("") }
    val weight = rememberSaveable{ mutableStateOf("") }
    //TODO: data checked
    if(appointment?.disease_details != ""){
        disease.value = appointment?.disease_details.toString()
    }
    if(appointment?.prescription != ""){
        prescription.value = appointment?.prescription.toString()
    }
    if(appointment?.cabin_no != ""){
        cabin.value = appointment?.cabin_no.toString()
    }
    if(appointment?.weight != ""){
        weight.value = appointment?.weight.toString()
    }


    if(isDialogShow) {
        Dialog(
            onDismissRequest = {
                isDialogShow = false
            },
            DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Box(
                contentAlignment= Alignment.Center,
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                Column(modifier = Modifier.padding(vertical = 30.dp)) {
                    if(nurseList.value != null){
                        Box( modifier = Modifier
                            .fillMaxWidth()
                        ) {
                            Row(horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically)
                            {
                                Spacer(modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.3f)
                                    .size(3.dp)
                                    .background(Color.Black))
                                Text(text = "All Nurse",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .weight(0.4f)


                                )
                                Spacer(modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.3f)
                                    .size(3.dp)
                                    .background(Color.Black))
                            }
                        }


                        LazyColumn {
                            items(nurseList.value!!) { nurseUser ->
                                Log.d("nurse", "InputPatientDetails: ${nurseUser.name}")
                                Box( modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 10.dp)
                                    .border(
                                        border = BorderStroke(1.dp, Color.Black),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                ) {
                                    Text(text = "Nurse: ${nurseUser.name}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp, vertical = 5.dp)
                                            .clickable {
                                                selectedNurseName.value = nurseUser.name
                                                selectedNurseUid.value = nurseUser.uid
                                                isDialogShow = false
                                            }

                                    )
                                }


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


            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                onClick = {
                    isDialogShow = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp, horizontal = 25.dp)
            ) {
                Text(text = if(appointment?.nurse_uid != "" )
                    "Nurse: ${nurseProfile.value?.name}"
                else if(selectedNurseName.value != ""){
                    "Nurse: ${selectedNurseName.value}"
                } else "Assign Nurse" )
            }


            Spacer(modifier = Modifier.height(15.dp))

            InputField(inputState = cabin,
                labelId = "Cabin No:",
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(15.dp))

            InputField(inputState = weight,
                labelId = "Weight:",
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(15.dp))

            InputField(
                inputState = disease,
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
                    if(disease.value == "" && prescription.value == "" ){
                        Toast.makeText(context, "Fill up all fields", Toast.LENGTH_SHORT).show()
                    } else {
                        val db = Firebase.firestore
                        val appointData: MutableMap<String, Any> = HashMap()
                        appointData["cabin_no"] = cabin.value
                        appointData["weight"] = weight.value
                        appointData["date"] = appointment?.date.toString()
                        appointData["disease_details"] = disease.value
                        appointData["doc_checked"] = true
                        appointData["doctor_uid"] = appointment?.doctor_uid.toString()
                        appointData["document_id"] = appointment?.document_id.toString()
                        appointData["nurse_uid"] = selectedNurseUid.value
                        appointData["patient_uid"] = appointment?.patient_uid.toString()
                        appointData["prescription"] = prescription.value
                        appointData["serial"] = appointment?.serial.toString()
                        appointData["time_slot"] = appointment?.time_slot.toString()


                        db.collection("appointment")
                            .document(document_id.toString())
                            .set(appointData)
                            .addOnSuccessListener {
                                isCheckPatient.value = false
                            }
                            .addOnFailureListener {
                                isCheckPatient.value = false
                                Toast.makeText(context, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show()
                            }

                        Log.d("NurseData", "appointData: ${appointData.toString()}")
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




