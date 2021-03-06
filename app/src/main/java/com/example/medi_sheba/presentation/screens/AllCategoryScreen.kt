package com.example.medi_sheba.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_sheba.model.categoryList
import com.example.medi_sheba.presentation.StaticScreen.CategoryCard
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.example.medi_sheba.presentation.util.gridItems
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.background

@Composable
fun AllCategoryScreen(navController: NavController) {
    Scaffold(
        topBar = {
            AppBar(navController = navController, title = "All Categories")
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
            ) { category ->
                CategoryCard(
                    modifier = Modifier.clickable {
                        navController.navigate(ScreenItem.AllDoctorsScreenItem.route + "/"
                                + category.cate_name)
                    },
                    name =  category.cate_name,
                    contentName =  category.cate_name,
                    painter = painterResource(category.cate_image))
            }
        }
    }
}
