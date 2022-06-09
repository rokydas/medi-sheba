package com.example.medi_sheba.presentation.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun encrypt(plainText: String): String {
    val encoder: Base64.Encoder = Base64.getEncoder()
    return encoder.encodeToString(plainText.toByteArray())
}

@RequiresApi(Build.VERSION_CODES.O)
fun decrypt(cipherText: String): String {
    val decoder: Base64.Decoder = Base64.getDecoder()
    return String(decoder.decode(cipherText))
}