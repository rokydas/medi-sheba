package com.example.medi_sheba.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.medi_sheba.model.doctors
import com.example.medi_sheba.ui.theme.PrimaryColor


@Preview
@Composable
fun AllTopDoctorsScreen(navController:NavController= NavController(LocalContext.current)){
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "All Top Doctors")
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
    }
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor)){
            items(doctors) { doctor ->
                DoctorHorizontalCard(doctor)
            }
        }


    }
}