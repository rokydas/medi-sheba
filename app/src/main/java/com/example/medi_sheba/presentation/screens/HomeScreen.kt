@file:JvmName("HomeScreenKt")

package com.example.medi_sheba.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.medi_sheba.R
import com.example.medi_sheba.controllers.AllDoctorsController
import com.example.medi_sheba.controllers.ProfileController
import com.example.medi_sheba.model.User
import com.example.medi_sheba.presentation.LineChart.LineChartContent
import com.example.medi_sheba.presentation.LineChart.LineChartScreen
import com.example.medi_sheba.presentation.StaticScreen.CategoryCard
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.background
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(navController: NavHostController, auth: FirebaseAuth) {

    val allDoctorsController = AllDoctorsController()
    allDoctorsController.getDoctors("All")
    val profileController = ProfileController()
    val doctors = allDoctorsController.doctors.observeAsState()
    val user = profileController.user.observeAsState()

    profileController.getUser(auth.currentUser!!.uid)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController,
            title = "Home") }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp)
                    .background(PrimaryColor)
            ) {
                item {
                    Spacer(modifier = Modifier.height(70.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Medi Sheba",
                            style = MaterialTheme.typography.h2,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                        Text(
                            text = "Your online health partner",
                            style = MaterialTheme.typography.h6,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxSize(),
                        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(background)
                                .padding(horizontal = 20.dp)
                        ) {
                            Column {
                                Spacer(modifier = Modifier.height(40.dp))
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Categories",
                                        style = MaterialTheme.typography.h5,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Text(
                                        text = "See all",
                                        style = MaterialTheme.typography.h6,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryColor,
                                        modifier = Modifier.clickable {
                                            navController.navigate(ScreenItem.AllCategoryScreen.route)
                                        }
                                    )
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                Row(
                                    horizontalArrangement = Arrangement.Center
                                ) {

                                    CategoryCard(
                                        modifier = Modifier.weight(1f),
                                        name =  "Cardiologist",
                                        contentName =  "Cardiologist",
                                        painter = painterResource(R.drawable.cardiologist))

                                    CategoryCard(
                                        modifier = Modifier.weight(1f),
                                        name =  "Orthopedic",
                                        contentName =  "Orthopedic",
                                        painter = painterResource(R.drawable.ortho))

                                    CategoryCard(
                                        modifier = Modifier.weight(1f),
                                        name =  "Dentist",
                                        contentName =  "Dentist",
                                        painter = painterResource(R.drawable.dentist))
                                }

                                Spacer(modifier = Modifier.height(25.dp))

                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Our Top Doctors",
                                        style = MaterialTheme.typography.h5,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Text(
                                        text = "See all",
                                        style = MaterialTheme.typography.h6,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryColor,
                                        modifier = Modifier.clickable {
                                            navController.navigate(ScreenItem.AllDoctorsScreenItem.route + "/All")
                                        }
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                }
                when {
                    doctors.value == null -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(background)
                                    .padding(15.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    doctors.value!!.isEmpty() -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(background)
                                    .padding(15.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "There is no doctor")
                            }
                        }
                    }
                    else -> {
                        val doctorList = if (doctors.value!!.size >= 5) doctors.value!!.subList(0, 5)
                        else doctors.value!!.subList(0, doctors.value!!.size)
                        items(doctorList) { doctor ->
                            DoctorHorizontalCard(doctor, navController, user.value!!)
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun DoctorHorizontalCard(doctor: User, navController: NavController, user: User) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
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
                    painter = painterResource(R.drawable.doctor2),
                    contentDescription = "profile_picture",
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
                        text = doctor.doctorDesignation,
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray
                    )
                    Text(
                        text = "৳ " + doctor.doctorPrice,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Row {
                    Icon(imageVector = Icons.Default.Star,
                        contentDescription = "star")
                    Text(
                        text = doctor.doctorRating.toString(),
                        style = MaterialTheme.typography.body1
                    )
                }
                Surface(
                    modifier = Modifier
                        .clip(shape = CircleShape.copy(all = CornerSize(5.dp)))
                ) {
                    Box(
                        modifier = Modifier
                            .background(PrimaryColor)
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .clip(
                                shape = CircleShape
                                    .copy(all = CornerSize(12.dp))
                            )
                            .clickable {
                                if (user.userType == "Patient") {
                                    navController.navigate(
                                        ScreenItem.BookAppointmentScreenItem.route +
                                                "/" + doctor.name + "/" + doctor.doctorDesignation + "/" + doctor.doctorPrice + "/" + doctor.uid
                                    )
                                } else {
                                    Toast.makeText(context, "Sorry, you are not a patient.", Toast.LENGTH_SHORT).show()
                                }

                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Book Appointment",
                            color = Color.White,
                            style = TextStyle(fontSize = 12.sp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

