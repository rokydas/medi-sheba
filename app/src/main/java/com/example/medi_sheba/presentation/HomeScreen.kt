package com.example.medi_sheba.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_sheba.R
import com.example.medi_sheba.model.Doctor
import com.example.medi_sheba.model.doctors
import com.example.medi_sheba.presentation.screenItem.Screen
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.background

@Composable
fun HomeScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
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
                    style = MaterialTheme.typography.h4,
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
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate(Screen.AllDoctorsScreen.route)
                                    }
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(5.dp)
                                    .shadow(5.dp, RoundedCornerShape(10.dp))
                                    .background(Color.White)
                                    .padding(10.dp)
                            ) {
                                Image(painter = painterResource(R.drawable.cardiologist), contentDescription = "")
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "Cardiologist")
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(5.dp)
                                    .shadow(5.dp, RoundedCornerShape(10.dp))
                                    .background(Color.White)
                                    .padding(10.dp)
                            ) {
                                Image(painter = painterResource(R.drawable.ortho), contentDescription = "")
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "Orthopedic")
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(5.dp)
                                    .shadow(5.dp, RoundedCornerShape(10.dp))
                                    .background(Color.White)
                                    .padding(10.dp)
                            ) {
                                Image(painter = painterResource(R.drawable.dentist), contentDescription = "")
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "Dentist")
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

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
                                color = PrimaryColor
                            )
                        }
                    }
                }
            }
        }
        items(doctors) { doctor ->
            DoctorHorizontalCard(doctor)
        }
    }
}

@Composable
fun DoctorHorizontalCard(doctor: Doctor) {
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
                    painter = painterResource(doctor.image),
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
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = doctor.designation,
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray
                    )
                    Text(
                        text = "৳ " + doctor.price,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Row {
                    Icon(imageVector = Icons.Default.Star, contentDescription = "star")
                    Text(
                        text = doctor.rating.toString(),
                        style = MaterialTheme.typography.body1
                    )
                }
                Box(
                    modifier = Modifier
                        .background(PrimaryColor)
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Book Now",
                        color = Color.White,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}