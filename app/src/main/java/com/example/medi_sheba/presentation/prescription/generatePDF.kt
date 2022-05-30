package com.example.medi_sheba.presentation.prescription

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.pdf.PdfDocument
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.medi_sheba.R
import com.google.type.DateTime
import kotlinx.datetime.LocalDateTime
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

fun generatePDF(context: Context , directory: File) {
    val pageHeight = 1120
    val pageWidth = 792
    val pdfDocument = PdfDocument()
    val paint = Paint()

    val myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
    val myPage = pdfDocument.startPage(myPageInfo)
    val canvas: Canvas = myPage.canvas
    val bitmap: Bitmap? = drawableToBitmap(context.resources.getDrawable(R.drawable.logo))
    val scaleBitmap: Bitmap? = Bitmap.createScaledBitmap(bitmap!!, 120, 120, false)
    canvas.drawBitmap(scaleBitmap!!, 40f, 40f, paint)

   generatePrescript(context, canvas)


    val time = Date().time
    Log.d("file", "time: $time     ")
    pdfDocument.finishPage(myPage)
    val file = File(directory, "medi-sheba_$time.pdf")
    Log.d("file", "generatePDF: $file")

    try {
        pdfDocument.writeTo(FileOutputStream(file))
        Toast.makeText(context, "PDF file generated successfully", Toast.LENGTH_SHORT).show()
    } catch (ex: IOException) {
        ex.printStackTrace()
    }
    pdfDocument.close()
}

fun generatePrescript(context: Context, canvas:Canvas) {
    val title = Paint()

    title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    title.textSize = 20f
    title.color = ContextCompat.getColor(context, R.color.primary_color)

    title.textAlign = Paint.Align.CENTER
    canvas.drawText("Medi Sheva", 500f, 100f, title)
    title.textAlign = Paint.Align.LEFT
    title.textSize = 15f
    canvas.drawText("This is Our Combined Project", 400f, 140f, title)
    canvas.drawText("This is created for medical", 400f, 160f, title)

//TODO:: Name
    title.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    title.color = ContextCompat.getColor(context, R.color.black)
    title.textSize = 10f
    title.textAlign = Paint.Align.LEFT
    canvas.drawText("Patient Name: ", 100f, 350f, title)

    title.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    title.color = ContextCompat.getColor(context, R.color.primary_color)
    title.textSize = 15f
    title.textAlign = Paint.Align.LEFT
    canvas.drawText("Sarose Datta.", 180f, 350f, title)

    //TODO:: Mobile Number
    title.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    title.color = ContextCompat.getColor(context, R.color.black)
    title.textSize = 10f
    title.textAlign = Paint.Align.LEFT
    canvas.drawText("Phone No.: ", 100f, 380f, title)

    title.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    title.color = ContextCompat.getColor(context, R.color.primary_color)
    title.textSize = 15f
    title.textAlign = Paint.Align.LEFT
    canvas.drawText("01516174937", 180f, 380f, title)

    //TODO:: Address
    title.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    title.color = ContextCompat.getColor(context, R.color.black)
    title.textSize = 10f
    title.textAlign = Paint.Align.LEFT
    canvas.drawText("Address: ", 100f, 410f, title)

    title.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    title.color = ContextCompat.getColor(context, R.color.primary_color)
    title.textSize = 15f
    title.textAlign = Paint.Align.LEFT
    canvas.drawText("Chawkbazar, Chittagong", 180f, 410f, title)

    //TODO:: Weight
    title.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    title.color = ContextCompat.getColor(context, R.color.black)
    title.textSize = 10f
    title.textAlign = Paint.Align.LEFT
    canvas.drawText("Weight: ", 100f, 440f, title)

    title.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    title.color = ContextCompat.getColor(context, R.color.primary_color)
    title.textSize = 15f
    title.textAlign = Paint.Align.LEFT
    canvas.drawText("75 kg", 180f, 440f, title)

    //TODO:: Disease
    title.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    title.color = ContextCompat.getColor(context, R.color.black)
    title.textSize = 10f
    title.textAlign = Paint.Align.LEFT
    canvas.drawText("Disease Details: ", 100f, 470f, title)

    title.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    title.color = ContextCompat.getColor(context, R.color.primary_color)
    title.textSize = 15f
    title.textAlign = Paint.Align.LEFT
    canvas.drawText("There are huge disease.", 180f, 470f, title)

    //TODO:: Prescription
    title.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    title.color = ContextCompat.getColor(context, R.color.black)
    title.textSize = 10f
    title.textAlign = Paint.Align.LEFT
    canvas.drawText("Prescription: ", 100f, 500f, title)

    title.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    title.color = ContextCompat.getColor(context, R.color.primary_color)
    title.textSize = 15f
    title.textAlign = Paint.Align.LEFT
    canvas.drawText("There are assigned tablet & capsule, to maintain your fat body.", 180f, 500f, title)

    //TODO:: Doctor
    title.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    title.color = ContextCompat.getColor(context, R.color.black)
    title.textSize = 10f
    title.textAlign = Paint.Align.LEFT
    canvas.drawText("Doctor: ", 100f, 530f, title)

    title.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    title.color = ContextCompat.getColor(context, R.color.primary_color)
    title.textSize = 15f
    title.textAlign = Paint.Align.LEFT
    canvas.drawText("Skillful Doctor ", 180f, 530f, title)

    //TODO:: Nurse
    title.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    title.color = ContextCompat.getColor(context, R.color.black)
    title.textSize = 10f
    title.textAlign = Paint.Align.LEFT
    canvas.drawText("Assigned Nurse: ", 100f, 560f, title)

    title.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
    title.color = ContextCompat.getColor(context, R.color.primary_color)
    title.textSize = 15f
    title.textAlign = Paint.Align.LEFT
    canvas.drawText("Beautiful Lady ", 180f, 560f, title)

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