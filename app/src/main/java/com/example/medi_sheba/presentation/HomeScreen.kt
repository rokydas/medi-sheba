package com.example.medi_sheba.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.PrimaryColorLight
import com.example.medi_sheba.ui.theme.background
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(navController: NavController, auth: FirebaseAuth) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor)
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Text(
            text = "Medi Sheba",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Your online health partner",
            style = MaterialTheme.typography.h6,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(50.dp))
        Card(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryColor),
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
                            color = PrimaryColor
                        )
                    }

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
}