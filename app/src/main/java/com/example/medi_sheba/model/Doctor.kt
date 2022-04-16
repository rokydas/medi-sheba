package com.example.medi_sheba.model

import com.example.medi_sheba.R

data class Doctor(
    val name: String,
    val designation: String,
    val price: Int,
    val rating: Float,
    val image: Int
)

val doctors = listOf<Doctor>(
    Doctor("Dr. Jenny Roy", "Heart Surgeon", 300, 4.8f, R.drawable.doctor2),
    Doctor("Dr. Jenny Roy", "Heart Surgeon", 300, 4.8f, R.drawable.doctor2),
    Doctor("Dr. Jenny Roy", "Heart Surgeon", 300, 4.8f, R.drawable.doctor2),
    Doctor("Dr. Jenny Roy", "Heart Surgeon", 300, 4.8f, R.drawable.doctor2),
    Doctor("Dr. Jenny Roy", "Heart Surgeon", 300, 4.8f, R.drawable.doctor2),
    Doctor("Dr. Jenny Roy", "Heart Surgeon", 300, 4.8f, R.drawable.doctor2),
    Doctor("Dr. Jenny Roy", "Heart Surgeon", 300, 4.8f, R.drawable.doctor2),
    Doctor("Dr. Jenny Roy", "Heart Surgeon", 300, 4.8f, R.drawable.doctor2),
    Doctor("Dr. Jenny Roy", "Heart Surgeon", 300, 4.8f, R.drawable.doctor2),
    Doctor("Dr. Jenny Roy", "Heart Surgeon", 300, 4.8f, R.drawable.doctor2),
)
