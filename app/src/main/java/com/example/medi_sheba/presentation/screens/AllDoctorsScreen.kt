package com.example.medi_sheba.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.medi_sheba.R
import com.example.medi_sheba.controllers.AllDoctorsController
import com.example.medi_sheba.controllers.ProfileController
import com.example.medi_sheba.model.User
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.example.medi_sheba.presentation.util.gridItems
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.background
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AllDoctorsScreen(navController: NavController, category: String?) {

    val allDoctorsController = AllDoctorsController()
    allDoctorsController.getDoctors(category!!)
    val doctors = allDoctorsController.doctors.observeAsState()

    val profileController = ProfileController()
    val user = profileController.user.observeAsState()
    val auth = FirebaseAuth.getInstance()

    profileController.getUser(auth.currentUser!!.uid)

    Scaffold(
        topBar = {
            AppBar(navController = navController, title = "$category Doctors")
        },
    ) {
        LazyColumn(
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier
                .background(background)
                .fillMaxSize()
        ) {
            if (doctors.value == null) {
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            else {
                gridItems(
                    data = doctors.value!!,
                    columnCount = 2,
                    modifier = Modifier
                ) { doctor ->
                    DoctorCard(doctor, navController, user.value!!)
                }
            }
        }
    }
}

@Composable
fun DoctorCard(
    doctor: User,
    navController: NavController,
    user: User
) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .shadow(5.dp, shape = RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(vertical = 15.dp, horizontal = 25.dp)
    ) {
        SubcomposeAsyncImage(
            model = doctor.image,
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
                        contentDescription = "course thumbnail"
                    )
                }
                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = doctor.name,
            style = MaterialTheme.typography.h6
        )
        Text(
            text = doctor.doctorDesignation,
            style = MaterialTheme.typography.body1,
            color = Color.Gray
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "৳ " + doctor.doctorPrice,
                style = MaterialTheme.typography.h6
            )
            Row {
                Icon(imageVector = Icons.Default.Star, contentDescription = "star")
                Text(
                    text = doctor.doctorRating.toString(),
                    style = MaterialTheme.typography.h6
                )
            }
        }
        Surface(
            modifier = Modifier
                .clip(shape =
                CircleShape.copy(all = CornerSize(5.dp)))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryColor)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .clickable {
                        if (user.userType == "Patient") {
                            navController.navigate(ScreenItem.BookAppointmentScreenItem.route +
                                    "/" + doctor.name + "/" + doctor.doctorDesignation + "/" + doctor.doctorPrice + "/" + doctor.uid
                            )
                        }
                        else {
                            Toast.makeText(context, "Sorry, you are not a patient.", Toast.LENGTH_SHORT).show()
                        }

                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Book Appointment",
                    color = Color.White,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}