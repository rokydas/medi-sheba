package com.example.medi_sheba.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medi_sheba.R
import com.example.medi_sheba.controllers.BookAppointmentController
import com.example.medi_sheba.presentation.encryption.EncryptClass
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.background
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookAppointmentScreen(
    navController: NavController,
    name: String?,
    designation: String?,
    price: String?,
    doctorUid: String?,
    encryptClass: EncryptClass
) {
    val calendarState = rememberSelectableCalendarState(
        initialSelection = listOf(LocalDate.now())
    )

    val context = LocalContext.current
    val bookAppointmentController = BookAppointmentController()
    val timeSlots = bookAppointmentController.timeSlots.observeAsState()

    bookAppointmentController.loadAllAppointmentsForBooking(doctorUid!!,
        calendarState.selectionState.selection, context, encryptClass)

    Scaffold (
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
        when(timeSlots.value) {
            null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            else -> {
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
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
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
                    if (timeSlots.value!!.isEmpty()) {
                        item {
                            Text(
                                text = "Please select a valid date",
                                style = MaterialTheme.typography.h6,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        itemsIndexed(timeSlots.value!!) { index, timeSlot ->
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
                                    Spacer(modifier = Modifier.width(30.dp))
                                    Text(
                                        text = (index + 1).toString() + ".",
                                        style = MaterialTheme.typography.h6,
                                        color = if (timeSlot.isBooked) Color.Gray else Color.Black,
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = timeSlot.time,
                                        style = MaterialTheme.typography.h6,
                                        color = if (timeSlot.isBooked) Color.Gray else Color.Black,
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
                                                        + "/" + doctorUid + "/" + timeSlot.time + "/" + (index + 1).toString()
                                                        + "/" + calendarState.selectionState.selection[0].toString() + "/" + name + "/" + designation
                                            )
                                        },
                                        enabled = !timeSlot.isBooked
                                    ) {
                                        Text(
                                            text = if (timeSlot.isBooked) "Booked" else "Book",
                                            color = if (timeSlot.isBooked) Color.Gray else Color.White,
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
    }
}