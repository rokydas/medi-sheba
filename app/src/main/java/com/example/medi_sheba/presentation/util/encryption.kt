package com.example.medi_sheba.presentation.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.UnsupportedEncodingException
import java.security.GeneralSecurityException
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun encrypt(plainText: String): String {
    val encoder: Base64.Encoder = Base64.getEncoder()
    return encoder.encodeToString(plainText.toByteArray())
}

@RequiresApi(Build.VERSION_CODES.O)
fun decrypt(cipherText: String): String {
    if(cipherText == "" || cipherText == "0" || cipherText == "0.0f" ){
        return ""
    }else{
        val decoder: Base64.Decoder = Base64.getDecoder()
        return String(decoder.decode(cipherText))

    }
}