package com.example.medi_sheba.presentation.prescription

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.medi_sheba.ui.theme.Purple500
import com.example.medi_sheba.R
import com.example.medi_sheba.presentation.ratingbar.CustomRatingBar
import com.example.medi_sheba.presentation.ratingbar.RatingBarConfig
import com.example.medi_sheba.presentation.ratingbar.RatingBarStyle
import java.io.File



private const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34

private fun foregroundPermissionApproved(context: Context): Boolean {
    val writePermissionFlag = PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
        context, Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    val readPermissionFlag = PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
        context, Manifest.permission.READ_EXTERNAL_STORAGE
    )

    return writePermissionFlag && readPermissionFlag
}

fun requestForegroundPermission(context: Context) {
    val provideRationale = foregroundPermissionApproved(context = context)
    if (provideRationale) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
        )
    } else {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
        )
    }
}

@Composable
fun PrescriptScreen() {
//    companion object {
        val initialRating = 1.5f
//    }
    var rating by rememberSaveable { mutableStateOf(initialRating) }

    val context = LocalContext.current
    requestForegroundPermission(context)
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
//                generatePDF(context, getDirectory(context), appointment)
            },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(60.dp)
                .padding(10.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(Purple500)
        ) {
            Text(
                text = "Submit",
                color = Color.White,
                fontSize = 13.sp
            )
        }


        Spacer(modifier = Modifier.height(20.dp))


        Text(
            text = "Current Rating Bar Value: $rating",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(30.dp))
        CustomRatingBar(
            value = rating,
            onValueChange = {
                rating = it
            },
            onRatingChanged = {
                Log.d("Rating Value", "RatingBarView: $it")
            },
            config = RatingBarConfig()
                .style(RatingBarStyle.HighLighted)
        )

    }
}



fun getDirectory(context: Context): File {
    val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
        File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists()) mediaDir
    else context.filesDir
}