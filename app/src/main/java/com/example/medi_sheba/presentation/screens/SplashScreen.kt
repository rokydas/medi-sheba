package com.example.medi_sheba.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_sheba.R
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController = NavController(LocalContext.current), auth: FirebaseAuth) {

    LaunchedEffect(key1 = true) {
        delay(1000)
        navController.popBackStack()

        val currentUser: FirebaseUser? = auth.currentUser

        if(currentUser == null) {
            navController.navigate(ScreenItem.IntroScreenItem.route)
        } else {
            navController.navigate(ScreenItem.ProfileScreenItem.route)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(painterResource(R.drawable.ic_trello_logo),"trello logo")
    }

}

