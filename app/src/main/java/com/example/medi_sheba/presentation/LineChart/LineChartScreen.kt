package com.example.medi_sheba.presentation.LineChart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.bytebeats.views.charts.line.LineChart
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.line.render.yaxis.SimpleYAxisDrawer


@Composable
fun LineChartScreen(){
    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 20.dp, horizontal = 5.dp)
        .shadow(5.dp, RoundedCornerShape(5.dp))
        .background(Color.White)){
        LineChartContent()
    }
}

@Composable
fun LineChartContent() {
    val lineChartData = LineChartDataModel()

    Column(
        modifier = Modifier.padding(
            horizontal = 15.dp,
            vertical = 15.dp
        )
    ) {
        Text(text = "Patient Weight Chart", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        LineChartRow(lineChartDataModel = lineChartData)
    }
}


@Composable
private fun LineChartRow(lineChartDataModel: LineChartDataModel) {
    Box(
        modifier = Modifier
            .height(250.dp)
            .fillMaxSize()
    ) {
        LineChart(
            lineChartData = lineChartDataModel.lineChartData,
            pointDrawer = lineChartDataModel.pointDrawer,
            lineDrawer = SolidLineDrawer(),
            xAxisDrawer = SimpleXAxisDrawer(),
            yAxisDrawer = SimpleYAxisDrawer(),
        )
    }
}