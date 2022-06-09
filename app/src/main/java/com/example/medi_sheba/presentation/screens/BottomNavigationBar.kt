package com.example.medi_sheba.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medi_sheba.R
import com.example.medi_sheba.presentation.screenItem.ScreenItem

data class BottomNavItem(
    val screen: ScreenItem,
    val icon: Int,
    val title: String
)

@Composable
fun BottomNavigationBar(navController: NavController, title: String) {
    val items = listOf(
        BottomNavItem(ScreenItem.AllAppointmentsScreenItem, R.drawable.appointment, "Appointment"),
        BottomNavItem(ScreenItem.HomeScreenItem, R.drawable.home, "Home"),
        BottomNavItem(ScreenItem.ChatUserListScreenItem, R.drawable.chat_icon, "Chat"),
        BottomNavItem(ScreenItem.ProfileScreenItem, R.drawable.profile_icon, "Profile"),
    )

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.White
    ) {
        items.forEach { item ->

            val isSelected = item.title == title

            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon), contentDescription = item.title,
                        modifier = Modifier.width(20.dp),
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = isSelected,
                onClick = {
                    navController.navigate(item.screen.route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                }
            )
        }
    }
}