package com.example.medi_sheba.presentation.encryption

import android.util.Base64
import android.util.Log
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.security.GeneralSecurityException
import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

class EncryptClass2  {
    @Inject
    constructor()
    private val ITERATION_COUNT: Int = 1000
    private val KEY_LENGTH: Int = 256
    private val PBKDF2_DERIVATION_ALGORITHM: String = "PBKDF2WithHmacSHA1"
    private val CIPHER_ALGORITHM: String = "AES/CBC/PKCS5Padding"
    private val PKCS5_SALT_LENGTH: Int = 32
    private val DELIMITER: String =  "]"
    private val random = SecureRandom()
    private val EncryptUID = "jabedrokyabsarsaruj"

    private val errDec: String = "Invalid encypted text format"

    fun encrypt( plainText: String, password: String = EncryptUID ): String{
        val salt: ByteArray = generateSalt()
        val key: SecretKey = deriveKey(password, salt)!!

        val testValue = "Hello, world!"

        val encodeValue = Base64.encode(testValue.toByteArray(), Base64.DEFAULT)
        val decodeValue = Base64.decode(encodeValue, Base64.DEFAULT)

        Log.d("ENCODE_DECODE", "defaultValue = $testValue")
        Log.d("ENCODE_DECODE", "encodeValue = " + String(encodeValue))
        Log.d("ENCODE_DECODE", "decodeValue = " + String(decodeValue))


        try {
            val cipher: Cipher = Cipher.getInstance(CIPHER_ALGORITHM)
            val iv: ByteArray = generateIv(cipher.blockSize)
            val ivParams: IvParameterSpec = IvParameterSpec(iv)
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParams)
            val cipherText: ByteArray = cipher.doFinal(plainText.toByteArray(Charset.forName("UTF-8")))

            if(salt != null){
                return String.format("%s%s%s%s%s",
                toBase64(salt),
                DELIMITER,
                toBase64(iv),
                DELIMITER,
                toBase64(cipherText))
            }

            return String.format("%s%s%s",
            toBase64(iv),
            DELIMITER,
            toBase64(cipherText))


        } catch (e: GeneralSecurityException){
            return errDec
//            throw RuntimeException(e)
        } catch (e: UnsupportedEncodingException){
//            throw RuntimeException(e)
            return errDec
        }

    }
    fun decrypt(cipherText: String, password: String = EncryptUID ): String{
       val fields: List<String> = cipherText.split(DELIMITER)
        if(fields.size != 3){
            return cipherText
        }

        val salt: ByteArray = fromBase64(fields[0])
        val iv: ByteArray = fromBase64(fields[1])
        val cipherBytes: ByteArray = fromBase64(fields[2])
        val key:SecretKey = deriveKey(password, salt)!!

        try {
            val cipher: Cipher = Cipher.getInstance(CIPHER_ALGORITHM)
            val ivParams = IvParameterSpec(iv)
            cipher.init(Cipher.DECRYPT_MODE, key, ivParams)
            val plainText: ByteArray = cipher.doFinal(cipherBytes)
            return String(plainText, Charset.forName("UTF-8"))

        } catch (e: GeneralSecurityException){
            return errDec
        } catch (e: UnsupportedEncodingException){
            return errDec
        }


    }
//    fun encypt_decrypt(cipherText: String):String{
//        return encrypt(decrypt(cipherText = cipherText))
//    }

    private fun fromBase64(base64: String): ByteArray {
        return Base64.decode(base64, Base64.NO_WRAP)
    }

    private fun toBase64(bytes: ByteArray): String {
        return Base64.encodeToString(bytes, Base64.NO_WRAP)

    }
    private fun fromBase641(base64: String): ByteArray {
        return Base64.decode(base64, Base64.NO_WRAP)
    }

    private fun toBase641(bytes: ByteArray): String {
        return Base64.encodeToString(bytes, Base64.NO_WRAP)

    }

    private fun generateIv(length: Int): ByteArray {
        val b = ByteArray(length)
        random.nextBytes(b)
        return b
    }

    private fun deriveKey(password: String, salt: ByteArray): SecretKey? {
        try {
            val keyFactory: SecretKeyFactory = SecretKeyFactory.getInstance(PBKDF2_DERIVATION_ALGORITHM)
            val keySpec: KeySpec = PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH )
            val keyBytes: ByteArray = keyFactory.generateSecret(keySpec).encoded
            return SecretKeySpec(keyBytes,"AES")
        } catch(e:GeneralSecurityException){

            return null
        }

    }

    private fun generateSalt(): ByteArray {
        val b = ByteArray(PKCS5_SALT_LENGTH)
        random.nextBytes(b)
        return b
    }


}