package com.example.medi_sheba.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
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
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_sheba.R
import com.example.medi_sheba.model.Doctor
import com.example.medi_sheba.model.categoryList
import com.example.medi_sheba.model.doctors
import com.example.medi_sheba.presentation.StaticScreen.CategoryCard
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.example.medi_sheba.presentation.util.gridItems
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.background

@Composable
fun AllCategoryScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "All Categories")
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
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier
                .background(background)
                .fillMaxSize()
        ) {

            gridItems(
                data = categoryList,
                columnCount = 2,
                modifier = Modifier
            ) { doctor ->
                CategoryCard(
                    modifier = Modifier.clickable {
                        navController.navigate(ScreenItem.AllDoctorsScreenItem.route)

                    },
                    name =  doctor.cate_name,
                    contentName =  doctor.cate_name,
                    painter = painterResource(doctor.cate_image))
            }

        }
    }
}
