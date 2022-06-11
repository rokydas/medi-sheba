@file:JvmName("HomeScreenKt")

package com.example.medi_sheba.presentation.screens

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.filled.Notifications
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
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.medi_sheba.R
import com.example.medi_sheba.controllers.AllDoctorsController
import com.example.medi_sheba.controllers.ProfileController
import com.example.medi_sheba.model.User
import com.example.medi_sheba.model.categoryList
import com.example.medi_sheba.presentation.StaticScreen.CategoryCard
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.background
import com.google.firebase.auth.FirebaseAuth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavController, auth: FirebaseAuth) {

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
                    .background(PrimaryColor)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "notification",
                            modifier = Modifier
                                .padding(25.dp)
                                .clickable {
                                    navController.navigate(ScreenItem.NotificationScreenItem.route)
                                },
                            tint = Color.White
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
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
                                    categoryList.subList(0, 3).forEach { category ->
                                        CategoryCard(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clickable {
                                                    navController.navigate(
                                                        ScreenItem.AllDoctorsScreenItem.route + "/"
                                                                + category.cate_name
                                                    )
                                                },
                                            name =  category.cate_name,
                                            contentName =  category.cate_name,
                                            painter = painterResource(category.cate_image)
                                        )
                                    }
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
                                    .height(120.dp)
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
                                    .padding(vertical = 50.dp)
                                    .padding(vertical = 30.dp)
                                ,
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "There is no doctor", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    else -> {
                        val doctorList = if (doctors.value!!.size >= 5) doctors.value!!.subList(0, 5)
                        else doctors.value!!.subList(0, doctors.value!!.size)
                        items(doctorList) { doctor ->
                            DoctorHorizontalCard(doctor, navController, user.value)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DoctorHorizontalCard(doctor: User, navController: NavController, user: User?) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp, vertical = 10.dp)
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(horizontal = 5.dp, vertical = 15.dp)
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            SubcomposeAsyncImage(
                model = doctor.image,
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
                            painter = painterResource(id = R.drawable.avartar),
                            contentDescription = "profile image"
                        )
                    }
                    else -> {
                        SubcomposeAsyncImageContent()
                    }
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = doctor.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                if (doctor.doctorDesignation != "") {
                    Text(
                        text = doctor.doctorDesignation,
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Row {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "star"
                    )
                    Text(
                        text = doctor.doctorRating,
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "৳ " + doctor.doctorPrice,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
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
                                if (user != null) {
                                    if (user.userType == "Patient") {
                                        if (doctor.doctorDesignation != "" && doctor.doctorCategory != "" && doctor.doctorPrice != "") {
                                            Log.d("value", "DoctorHorizontalCard: ${doctor.name} ${doctor.doctorDesignation} ${doctor.doctorPrice} ")
                                            navController.navigate(
                                                ScreenItem.BookAppointmentScreenItem.route +
                                                        "/" + doctor.name + "/" + doctor.doctorDesignation + "/" + doctor.doctorPrice + "/" + doctor.uid
                                            )
                                        } else {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Sorry, this doctor is not ready yet",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }
                                    } else {
                                        Toast
                                            .makeText(
                                                context,
                                                "Sorry, you are not a patient.",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Appointment",
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

