package com.example.medi_sheba.presentation.screens

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.medi_sheba.R
import com.example.medi_sheba.controllers.ProfileController
import com.example.medi_sheba.model.Appointment
import com.example.medi_sheba.model.User
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.example.medi_sheba.presentation.util.decrypt
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.background
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.json.JSONException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(navController: NavController, auth: FirebaseAuth) {
    val context = LocalContext.current
    val userId = auth.uid
    val profileController = ProfileController()
    val db = Firebase.firestore
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var _user by rememberSaveable { mutableStateOf(User()) }

    val user = profileController.user.observeAsState()
    if(user.value != null) {
        isLoading = false
        _user = user.value!!
    }

    if(userId != null) {
        profileController.getUser(userId)
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, title = "Profile") },
        backgroundColor = background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if(isLoading) {
                Dialog(
                    onDismissRequest = {  },
                    DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                ) {
                    Box(
                        contentAlignment= Alignment.Center,
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                    ) {
                        CircularProgressIndicator(color = PrimaryColor)
                    }
                }
            }
            else {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "back",
                        modifier = Modifier
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = "update profile",
                        modifier = Modifier
                            .clickable {
                                navController.currentBackStackEntry?.savedStateHandle?.set("user", user.value)
                                navController.navigate(ScreenItem.UpdateProfileScreenItem.route)
                            }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 30.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = user.value?.image,
                        contentDescription = "profile image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
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
                                    painter = painterResource(id = R.drawable.avartar),
                                    contentDescription = "profile image"
                                )
                            }
                            else -> {
                                SubcomposeAsyncImageContent()
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(30.dp))

                    Column {
                        Text(
                            text = _user.name,
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = _user.userType,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier
                        .padding(start = 30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call, contentDescription = "",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(
                        text = "Mobile: " + _user.mobileNumber,
                        style = MaterialTheme.typography.body1
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .padding(start = 30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Mail, contentDescription = "",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(
                        text = "Email: " + _user.email,
                        style = MaterialTheme.typography.body1
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .padding(start = 30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call, contentDescription = "",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(
                        text = "Age: " + _user.age,
                        style = MaterialTheme.typography.body1
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .padding(start = 30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call, contentDescription = "",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(
                        text = "Address: " + _user.address,
                        style = MaterialTheme.typography.body1
                    )
                }
                if(_user.userType == "Admin") {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .clickable {
                                navController.navigate(ScreenItem.DashboardScreenItem.route)
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout, contentDescription = "",
                            tint = Color.Gray,
                        )
                        Spacer(modifier = Modifier.width(30.dp))
                        Text(
                            text = "Dashboard",
                            color = Color.Gray,
                            style = MaterialTheme.typography.h6
                        )
                    }


                    //TODO:: notification
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .clickable {
                                val docRef = db.collection("appointment")
                                    .whereEqualTo("reminderStatus", false)
                                docRef.get()
                                    .addOnSuccessListener { result ->
                                        val appointments = mutableListOf<Appointment>()
                                        for (document in result) {
                                            val appointment = document.toObject(Appointment::class.java)
                                            appointment.document_id = document.id
                                            appointment.time_slot = decrypt(appointment.time_slot)
                                            appointments.add(appointment)

                                            if(appointment.fcmToken != ""){
                                                getData(context, db, appointment.document_id, appointment.fcmToken)
                                            }


                                        }
                                    }
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications, contentDescription = "",
                            tint = Color.Gray,
                        )
                        Spacer(modifier = Modifier.width(30.dp))
                        Text(
                            text = "Notification Push",
                            color = Color.Gray,
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .padding(start = 30.dp)
                        .clickable {
                            auth.signOut()
                            navController.navigate(ScreenItem.IntroScreenItem.route) {
                                popUpTo(0)
                            }
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout, contentDescription = "",
                        tint = Color.Red,
                    )
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(
                        text = "Log out",
                        color = Color.Red,
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}
fun getData(context: Context, db: FirebaseFirestore, documentId: String, fcmToken: String) {
    Log.d("res", "getData fcmToken: $fcmToken")
    val myUrl = "https://snakehumanconflict.org/venom_resource_center/vrc_image/sendPushNotification.php"
    val stringRequest: StringRequest = object : StringRequest( Method.POST, myUrl,
        Response.Listener { response ->
            try {
                Log.d("res", "getData: $response")
                db.collection("appointment").document(documentId)
                    .update("reminderStatus", true)
                    .addOnSuccessListener {
                        return@addOnSuccessListener
                    }

            } catch (e: JSONException) {
                Log.d("res", "getData: $e")

                e.printStackTrace()
            }
        },
        Response.ErrorListener { error ->
            Log.d("res", "getData: $error")

//            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
        }) {
        override fun getParams(): Map<String, String> {
            val params: MutableMap<String, String> = HashMap()
            //Change with your post params
            params["title"] = "Appointment"
            params["message"] = "Please Check your appointment"
            params["fcm_token"] = fcmToken
            return params
        }
    }
    val requestQueue = Volley.newRequestQueue(context)
    requestQueue.add(stringRequest)


}
