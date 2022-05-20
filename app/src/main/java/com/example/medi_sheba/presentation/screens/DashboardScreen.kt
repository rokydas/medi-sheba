package com.example.medi_sheba.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_sheba.R
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.background

@Composable
fun DashboardScreen(navController: NavController) {

    Scaffold(
        topBar = {
            AppBar(navController = navController, title = "Dashboard")
        },
        backgroundColor = background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MakeDeleteBox(navController = navController, type = "Doctor")
            MakeDeleteBox(navController = navController, type = "Nurse")
            MakeDeleteBox(navController = navController, type = "Admin")
        }
    }
}

@Composable
fun MakeDeleteBox(navController: NavController, type: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 80.dp, vertical = 10.dp)
            .shadow(5.dp, shape = RoundedCornerShape(10.dp))
            .background(Color.White)
            .clickable {
                navController.navigate(
                    ScreenItem.MakeAndDeleteRoleItem.route
                            + "/$type"
                )
            }
            .padding(vertical = 15.dp, horizontal = 25.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.doctor2),
                contentDescription = "avartar",
                modifier = Modifier.width(80.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Make & delete $type",
                style = MaterialTheme.typography.h6
            )
        }
    }
}
