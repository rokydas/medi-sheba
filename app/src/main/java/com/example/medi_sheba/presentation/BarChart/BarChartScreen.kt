package com.example.medi_sheba.presentation.BarChart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.medi_sheba.controllers.BarChartController
import com.example.medi_sheba.presentation.prescription.PrescriptScreen
import me.bytebeats.views.charts.bar.BarChart


@Composable
fun BarChartContent(patient_uid: String, doctor_uid:String ) {
    Column(
        modifier = Modifier.padding(
            horizontal = 15.dp,
            vertical = 15.dp
        )
    ) {
        Text(text = "Patient Weight Chart")
        BarChartRow(patient_uid, doctor_uid)

    }
}

@Composable
private fun BarChartRow(patient_uid: String, doctor_uid:String) {
    val barChartController = BarChartController()
    barChartController.getBarChartList(patient_uid, doctor_uid)
    val pointList = barChartController.barchartList.observeAsState()

    if(pointList.value != null && pointList.value?.size != 0){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(vertical = 30.dp)
        ) {
            BarChart(
                barChartData = barChartController.getBarChart(pointList.value!!),
                labelDrawer = barChartController.labelDrawer,

                )
        }
    }
}
