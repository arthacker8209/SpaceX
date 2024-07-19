package com.deepak.spacex.common.utils

import java.time.LocalTime

class Utils {
    companion object {

        fun getTimeOfDay(): String {
            val currentTime = LocalTime.now()
            return when (currentTime.hour) {
                in 0..11 -> "Morning"
                in 12..16 -> "Afternoon"
                else -> "Evening"
            }
        }

    }
}