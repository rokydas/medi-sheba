package com.example.medi_sheba.model

data class DataOrException<T, E : Exception?>(
    var data: T? = null,
    var e: E? = null
)