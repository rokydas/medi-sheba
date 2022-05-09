package com.example.medi_sheba.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.medi_sheba.presentation.screenItem.ScreenItem

@Composable
fun DashboardScreen(navController: NavController) {

    Scaffold {
        Column(

        ) {
            Text(
                text = "Make & delete Doctor",
                modifier = Modifier.clickable {
                    navController.navigate(
                        ScreenItem.MakeAndDeleteRoleItem.route
                        + "/" + "Doctor"
                    )
                }
            )
            Text(
                text = "Make & delete Nurse",
                modifier = Modifier.clickable {
                    navController.navigate(
                        ScreenItem.MakeAndDeleteRoleItem.route
                        + "/" + "Nurse"
                    )
                }
            )
            Text(
                text = "Make & delete Admin",
                modifier = Modifier.clickable {
                    navController.navigate(
                        ScreenItem.MakeAndDeleteRoleItem.route
                        + "/" + "Admin"
                    )
                }
            )
        }
    }
}