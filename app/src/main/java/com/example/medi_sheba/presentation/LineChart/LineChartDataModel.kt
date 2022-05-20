package com.example.medi_sheba.presentation.LineChart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.point.EmptyPointDrawer
import me.bytebeats.views.charts.line.render.point.FilledCircularPointDrawer
import me.bytebeats.views.charts.line.render.point.HollowCircularPointDrawer
import me.bytebeats.views.charts.line.render.point.IPointDrawer
import kotlin.random.Random

class LineChartDataModel {
    var lineChartData by mutableStateOf(
        LineChartData(
            points = listOf(
                LineChartData.Point(50f, "03-05-2022"),
                LineChartData.Point(71f, "08-05-2022"),
                LineChartData.Point(72f, "08-05-2022"),
                LineChartData.Point(73f, "08-05-2022"),
                LineChartData.Point(74f, "08-05-2022"),
                LineChartData.Point(75f, "08-05-2022"),
                LineChartData.Point(76f, "08-05-2022"),
                LineChartData.Point(77f, "08-05-2022"),
                LineChartData.Point(78f, "08-05-2022"),
                LineChartData.Point(90f, "13-05-2022")
            ),
            padBy = 1f
        )
    )

    var horizontalOffset by mutableStateOf(5F)
    var pointDrawerType by mutableStateOf(PointDrawerType.Hollow)
    val pointDrawer: IPointDrawer
        get() {
            return when (pointDrawerType) {
                PointDrawerType.None -> EmptyPointDrawer
                PointDrawerType.Filled -> FilledCircularPointDrawer()
                PointDrawerType.Hollow -> HollowCircularPointDrawer()
            }
        }

    private fun randomYValue(): Float = Random.Default.nextInt(45, 145).toFloat()
}