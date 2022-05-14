package com.example.medi_sheba.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.medi_sheba.R
import com.example.medi_sheba.controllers.BookAppointmentController
import com.example.medi_sheba.model.BookAppointment
import com.example.medi_sheba.model.TimeSlot
import com.example.medi_sheba.model.doctors
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.background
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState

@Composable
fun BookAppointmentScreen(
    navController: NavController,
    name: String?,
    designation: String?,
    price: String?,
    doctorUid: String?
) {
    val calendarState = rememberSelectableCalendarState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Book Appointment")
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
        backgroundColor = background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp, horizontal = 25.dp)
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(R.drawable.doctor2),
                            contentDescription = "profile_picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.Gray, CircleShape)
                        )
                        Text(text = name ?: "")
                        Text(text = designation ?: "")
                        Text(text = "৳ $price")
                    }
                }
                SelectableCalendar(
                    modifier = Modifier.padding(horizontal = 70.dp),
                    calendarState = calendarState,
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
            if (calendarState.selectionState.selection.isNotEmpty()) {
                itemsIndexed(timeSlots) { index, timeSlot ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                            .background(Color.White)
                            .shadow(4.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(25.dp))
                            Text(
                                text = (index + 1).toString() + ".",
                                style = MaterialTheme.typography.h6,
                                color = if(timeSlot.isBooked) Color.Gray else Color.Black,
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = timeSlot.time,
                                style = MaterialTheme.typography.h6,
                                color = if(timeSlot.isBooked) Color.Gray else Color.Black,
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Button(
                                modifier = Modifier
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                                    .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
                                colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryColor),
                                onClick = {
                                    // boot an appointment
                                    navController.navigate(
                                        ScreenItem.PaymentScreenItem.route
                                             + "/" + doctorUid + "/" + timeSlot.time + "/" + (index+1).toString()
                                             + "/" + calendarState.selectionState.selection[0].toString() + "/" + name + "/" + designation
                                    )
                                },
                                enabled = !timeSlot.isBooked
                            ) {
                                Text(
                                    text = if(timeSlot.isBooked) "Booked" else "Book",
                                    color = if(timeSlot.isBooked) Color.Gray else Color.White,
                                    style = TextStyle(fontSize = 14.sp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

val timeSlots = mutableListOf<TimeSlot>(
    TimeSlot("09.01 AM - 09.30 AM", false),
    TimeSlot("09.31 AM - 10.00 AM", true),
    TimeSlot("10.01 AM - 10.30 AM", false),
    TimeSlot("10.31 AM - 11.00 AM", true),
    TimeSlot("11.01 AM - 11.30 AM", false),
    TimeSlot("11.31 AM - 12.00 PM", false),
    TimeSlot("12.01 PM - 12.30 PM", false),
    TimeSlot("12.31 PM - 01.00 PM", false),
    TimeSlot("07.01 PM - 07.30 PM", true),
    TimeSlot("07.31 PM - 08.00 PM", false),
    TimeSlot("08.01 PM - 08.30 PM", false),
    TimeSlot("08.31 PM - 09.00 PM", true),
    TimeSlot("09.01 PM - 09.30 PM", false),
    TimeSlot("09.31 PM - 10.00 PM", true)
)