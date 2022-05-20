package com.example.medi_sheba.model

import com.example.medi_sheba.R

data class Category(
    val cate_id: Int,
    val cate_name: String,
    val cate_image: Int
)

val categoryList = listOf<Category>(
    Category(1, "Cardiologist", R.drawable.cardiologist),
    Category(2, "Orthopedic", R.drawable.ortho),
    Category(3, "Dentist", R.drawable.dentist),
    Category(4, "Neurologists", R.drawable.neuro),
    Category(5, "Child Specialist", R.drawable.child),
    Category(6, "Medicine", R.drawable.medicine),
    Category(7, "Eye Specialist", R.drawable.eye),
    Category(8, "Surgery", R.drawable.surgery),
    Category(9, "Kidney specialist", R.drawable.kidney),
    Category(10, "Liver Specialist", R.drawable.liver),
)
