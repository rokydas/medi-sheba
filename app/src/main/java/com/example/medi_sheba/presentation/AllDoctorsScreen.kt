package com.example.medi_sheba.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_sheba.model.Doctor
import com.example.medi_sheba.model.doctors
import com.example.medi_sheba.presentation.util.gridItems
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.background

@Composable
fun AllDoctorsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "All Doctors")
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
        LazyColumn(
            modifier = Modifier
                .background(background)
                .fillMaxSize()
        ) {
            gridItems(
                data = doctors,
                columnCount = 2,
                modifier = Modifier
            ) { doctor ->
                DoctorCard(doctor)
            }
        }
    }
}

@Composable
fun DoctorCard(doctor: Doctor) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .shadow(5.dp, shape = RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(vertical = 15.dp, horizontal = 25.dp)
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
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = doctor.name,
            style = MaterialTheme.typography.h6
        )
        Text(
            text = doctor.designation,
            style = MaterialTheme.typography.body1,
            color = Color.Gray
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "৳ " + doctor.price,
                style = MaterialTheme.typography.h6
            )
            Row {
                Icon(imageVector = Icons.Default.Star, contentDescription = "star")
                Text(
                    text = doctor.rating.toString(),
                    style = MaterialTheme.typography.h6
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(PrimaryColor)
                .padding(5.dp)
                .clickable {
                    // todo: book now button handler
                },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Book Now",
                color = Color.White,
                style = MaterialTheme.typography.h6
            )
        }
    }
}