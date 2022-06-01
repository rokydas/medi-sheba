package com.example.medi_sheba.presentation.prescription

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.util.Log
import android.view.View.MeasureSpec
import android.widget.TextView
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import com.example.medi_sheba.R
import com.example.medi_sheba.controllers.ProfileController
import com.example.medi_sheba.model.Appointment
import com.example.medi_sheba.model.User
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.HashMap


fun generatePDF(
    context: Context,
    directory: File,
    data_map: HashMap<String, String>
) {



    val pageHeight = 1120
    val pageWidth = 792
    val pdfDocument = PdfDocument()
    val paint = Paint()

    val myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
    val myPage = pdfDocument.startPage(myPageInfo)
    val canvas: Canvas = myPage.canvas
    val bitmap: Bitmap? = drawableToBitmap(context.resources.getDrawable(R.drawable.logo, null))
    val scaleBitmap: Bitmap? = Bitmap.createScaledBitmap(bitmap!!, 70, 70, false)
    canvas.drawBitmap(scaleBitmap!!, 150f, 100f, paint)

   generatePrescript(context, canvas, paint, data_map)

    val time = Date().time
    pdfDocument.finishPage(myPage)
    val file = File(directory, "medi-sheba_$time.pdf")

    try {
        pdfDocument.writeTo(FileOutputStream(file))
        Toast.makeText(context, "PDF file generated successfully", Toast.LENGTH_SHORT).show()
    } catch (ex: IOException) {
        ex.printStackTrace()
    }
    pdfDocument.close()
}

fun generatePrescript(
    context: Context,
    canvas: Canvas,
    paint: Paint,
    data_map: HashMap<String, String>
) {
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    paint.textSize = 40f
    paint.color = ContextCompat.getColor(context, R.color.primary_color)
    paint.textAlign = Paint.Align.LEFT
    canvas.drawText("Medi Sheva", 320f, 175f, paint)
    paint.textAlign = Paint.Align.LEFT
    paint.textSize = 15f

//TODO:: Doctor Name
    val bitmap1: Bitmap? = drawableToBitmap(context.resources.getDrawable(R.drawable.ic_person, null))
    val scaleBitmap1: Bitmap? = Bitmap.createScaledBitmap(bitmap1!!, 40, 40, false)
    canvas.drawBitmap(scaleBitmap1!!, 100f, 275f, paint)

    paint.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    paint.color = ContextCompat.getColor(context, R.color.black)
    paint.textSize = 20f
    paint.textAlign = Paint.Align.LEFT
    canvas.drawText("${data_map["doctor_name"]}", 165f, 300f, paint)

    //specialist
    paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    paint.color = ContextCompat.getColor(context, R.color.black)
    paint.textSize = 15f
    paint.textAlign = Paint.Align.LEFT
    canvas.drawText("${data_map["doctor_category"]}", 165f, 325f, paint)

    //designation
    paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    paint.color = ContextCompat.getColor(context, R.color.black)
    paint.textSize = 15f
    paint.textAlign = Paint.Align.LEFT
    canvas.drawText("${data_map["doctor_designation"]}", 165f, 350f, paint)

    //TODO:: Doctor phn number
    val bitmap2: Bitmap? = drawableToBitmap(context.resources.getDrawable(R.drawable.ic_add_call, null))
    val scaleBitmap2: Bitmap? = Bitmap.createScaledBitmap(bitmap2!!, 30, 30, false)
    canvas.drawBitmap(scaleBitmap2!!, 500f, 275f, paint)

    paint.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    paint.color = ContextCompat.getColor(context, R.color.black)
    paint.textSize = 15f
    paint.textAlign = Paint.Align.LEFT
    canvas.drawText("${data_map["doctor_phn"]}", 550f, 290f, paint)

    //address
    paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    paint.color = ContextCompat.getColor(context, R.color.black)
    paint.textSize = 15f
    paint.textAlign = Paint.Align.LEFT
    canvas.drawText("${data_map["doctor_address"]}", 550f, 315f, paint)





//TODO:: Name
    paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    paint.color = ContextCompat.getColor(context, R.color.black)
    paint.textSize = 15f
    paint.textAlign = Paint.Align.LEFT
    canvas.drawText("Patient Name: ", 100f, 440f, paint)

    paint.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    paint.color = ContextCompat.getColor(context, R.color.primary_color)
    paint.textSize = 20f
    paint.textAlign = Paint.Align.LEFT
    canvas.drawText("${data_map["name"]}", 230f, 440f, paint)

    //TODO:: Mobile Number
    paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    paint.color = ContextCompat.getColor(context, R.color.black)
    paint.textSize = 15f
    paint.textAlign = Paint.Align.LEFT
    canvas.drawText("Phone No.: ", 100f, 480f, paint)

    paint.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    paint.color = ContextCompat.getColor(context, R.color.primary_color)
    paint.textSize = 20f
    paint.textAlign = Paint.Align.LEFT
    canvas.drawText("${data_map["phn"]}", 230f, 480f, paint)

    //TODO:: Address
    paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    paint.color = ContextCompat.getColor(context, R.color.black)
    paint.textSize = 15f
    paint.textAlign = Paint.Align.LEFT
    canvas.drawText("Address: ", 100f, 520f, paint)

    paint.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    paint.color = ContextCompat.getColor(context, R.color.primary_color)
    paint.textSize = 20f
    paint.textAlign = Paint.Align.LEFT
    canvas.drawText("${data_map["address"]}", 230f, 520f, paint)

    //TODO:: Weight
    paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    paint.color = ContextCompat.getColor(context, R.color.black)
    paint.textSize = 15f
    paint.textAlign = Paint.Align.LEFT
    canvas.drawText("Weight: ", 100f, 560f, paint)

    paint.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    paint.color = ContextCompat.getColor(context, R.color.primary_color)
    paint.textSize = 20f
    paint.textAlign = Paint.Align.LEFT
    canvas.drawText("${data_map["weight"]} Kg", 230f, 560f, paint)

    if(data_map["nurse_name"].toString() != "null"){
        //TODO:: Nurse
        paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        paint.color = ContextCompat.getColor(context, R.color.black)
        paint.textSize = 15f
        paint.textAlign = Paint.Align.LEFT
        canvas.drawText("Nurse: ", 100f, 600f, paint)

        paint.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        paint.color = ContextCompat.getColor(context, R.color.primary_color)
        paint.textSize = 20f
        paint.textAlign = Paint.Align.LEFT
        canvas.drawText("${data_map["nurse_name"]} ", 230f, 600f, paint)
    }

    if(data_map["disease"].toString() != "null"){
        //TODO:: Disease
        paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        paint.color = ContextCompat.getColor(context, R.color.black)
        paint.textSize = 15f
        paint.textAlign = Paint.Align.LEFT
        canvas.drawText("Disease Details: ", 100f, 640f, paint)

        val deseaseValue = data_map["disease"]
        val tv1 = TextView(context)
        tv1.setTextColor(PrimaryColor.toArgb())
        tv1.textSize = 10f
        tv1.text = deseaseValue
        tv1.typeface= Typeface.defaultFromStyle(Typeface.BOLD)
        tv1.measure(
            MeasureSpec.makeMeasureSpec(canvas.width-210, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(canvas.height, MeasureSpec.EXACTLY)
        )
        tv1.layout(0, 0, tv1.measuredWidth, tv1.measuredHeight)
        canvas.drawBitmap(tv1.drawToBitmap(), 230f, 620f, paint)
    }


    if(data_map["prescript"].toString() != "null"){
        //TODO:: Prescription
        paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        paint.color = ContextCompat.getColor(context, R.color.black)
        paint.textSize = 15f
        paint.textAlign = Paint.Align.LEFT
        canvas.drawText("Prescription: ", 100f, 760f, paint)

        val prescriptValue = data_map["prescript"]
        val tv = TextView(context)
        tv.setTextColor(PrimaryColor.toArgb())
        tv.textSize = 10f
        tv.text = prescriptValue
        tv.typeface= Typeface.defaultFromStyle(Typeface.BOLD)
//        tv.isDrawingCacheEnabled = true
        tv.measure(
            MeasureSpec.makeMeasureSpec(canvas.width-210, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(canvas.height, MeasureSpec.EXACTLY)
        )
        tv.layout(0, 0, tv.measuredWidth, tv.measuredHeight)
        canvas.drawBitmap(tv.drawToBitmap(), 230f, 740f, paint)


    }

    val dateFormatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeFormatter.ofPattern("dd:MM:yyyy h:mm:s a")
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.now()
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    val formattedDate = current.format(dateFormatter)

    paint.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    paint.color = ContextCompat.getColor(context, R.color.primary_color)
    paint.textSize = 20f
    paint.textAlign = Paint.Align.LEFT
    canvas.drawText("Prescription downloaded at $formattedDate", 120f, 1100f, paint)





}

fun drawableToBitmap(drawable: Drawable): Bitmap? {
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }
    val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}