package com.example.medi_sheba.model

import com.example.medi_sheba.R

data class MediCategory(
    val cate_id: Int,
    val cate_name: String,
    val cate_image: Int
)

val categoryList = listOf<MediCategory>(
    MediCategory(1, "Cardiologist", R.drawable.cardiologist),
    MediCategory(2, "Orthopedic", R.drawable.ortho),
    MediCategory(3, "Dentist", R.drawable.dentist),
    MediCategory(4, "Cardiologist", R.drawable.cardiologist),
    MediCategory(5, "Orthopedic", R.drawable.ortho),
    MediCategory(6, "Dentist", R.drawable.dentist),
    MediCategory(7, "Cardiologist", R.drawable.cardiologist),
    MediCategory(8, "Orthopedic", R.drawable.ortho),
    MediCategory(9, "Dentist", R.drawable.dentist),
    MediCategory(10, "Cardiologist", R.drawable.cardiologist),
    MediCategory(11, "Orthopedic", R.drawable.ortho),
    MediCategory(12, "Dentist", R.drawable.dentist),
    )
