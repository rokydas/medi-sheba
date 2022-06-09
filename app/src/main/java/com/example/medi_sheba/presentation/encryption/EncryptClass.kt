package com.example.medi_sheba.presentation.encryption

import android.util.Base64
import android.util.Log
import javax.inject.Inject

class EncryptClass  {
    @Inject
    constructor()

    fun encrypt( plainText: String ): String{
        val encodeValue = Base64.encode(plainText.toByteArray(), Base64.DEFAULT)
        return encodeValue.toString()

    }
    fun decrypt(cipherText: String ): ByteArray{
        Log.d("enc", "decrypt: $cipherText")
        return Base64.decode(cipherText, Base64.DEFAULT)
    }

}