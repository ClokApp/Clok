package com.kingfu.clok.stopwatch.styles

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import kotlin.math.sin

//class RGB(stopwatchPreferences: StopwatchPreferences): StopwatchViewModel(stopwatchPreferences) {
//
//     fun stopwatchUpdateStartAndEndRGB(
//        refreshRate: Float,
////        stopwatchColorCounter: Double,
////        stopwatchMinColorList: SnapshotStateList<Int>,
////        stopwatchSecColorList: SnapshotStateList<Int>,
////        stopwatchMsColorList: SnapshotStateList<Int>,
//    ) {
//        val frequency = 0.9
//        val phase = 1.5
//        val width = 128
//        val center = 127
//        val minOffset = 0
//        val secOffset = 3
//        val msOffset = 6
//
//        val temp: Double =
//            if (refreshRate <= 25) {
//                0.025
//            } else if (refreshRate > 25 && refreshRate <= 50) {
//                0.0025
//            } else if (refreshRate > 50 && refreshRate <= 75) {
//                0.00025
//            } else {
//                0.000025
//            }
//        stopwatchColorCounter =
//            (stopwatchColorCounter + ((refreshRate / 200) + temp)) % Double.MAX_VALUE
//        for (i in 0 until 6) {
//            stopwatchMinColorList[i] =
//                (sin(frequency * stopwatchColorCounter + phase * (i + minOffset)) * width + center).toInt()
//            stopwatchSecColorList[i] =
//                (sin(frequency * stopwatchColorCounter + phase * (i + secOffset)) * width + center).toInt()
//            stopwatchMsColorList[i] =
//                (sin(frequency * stopwatchColorCounter + phase * (i + msOffset)) * width + center).toInt()
//        }
//
//    }
//}