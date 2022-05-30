package com.example.medi_sheba.controllers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import me.bytebeats.views.charts.bar.BarChartData
import me.bytebeats.views.charts.bar.render.label.SimpleLabelDrawer

class BarChartController {
    val db = Firebase.firestore

    private val _barChartList = MutableLiveData<List<BarChartData.Bar>>()
    val barchartList: LiveData<List<BarChartData.Bar>>
        get() = _barChartList


    var labelDrawer by mutableStateOf(SimpleLabelDrawer(drawLocation = SimpleLabelDrawer.DrawLocation.XAxis))
        private set


    fun getBarChartList(patient_uid: String, doctor_uid:String) {
        val appointmentCol = db.collection("appointment")
        val barChartData = mutableListOf<BarChartData.Bar>()

        var count = 0

        appointmentCol
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    for (doc in document) {
                        if(doc.getString("patient_uid") == patient_uid && count<5 && doc.getString("doctor_uid") == doctor_uid ){
                            val barchartModel = BarChartData.Bar(
                                value = (if(doc.getString("weight") != "" && doc.getString("weight") != null )
                                    doc.getString("weight")?.toFloat()
                                else
                                    0f)!!,
                                color = Color(0XFF607D8B),
                                label = doc.getString("date").toString()
                            )
                            barChartData.add(barchartModel)

                            count++

                        }
                    }
                    _barChartList.value = barChartData
                }
            }
    }

    fun getBarChart(list: List<BarChartData.Bar>): BarChartData {
        return BarChartData(
            bars = list,
            padBy = 10f
        )
    }
}