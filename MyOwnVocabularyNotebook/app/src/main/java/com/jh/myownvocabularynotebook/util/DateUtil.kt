package com.jh.myownvocabularynotebook.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {
        fun getCurrentTime(): String {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("YYYYMMDDHHmmssSSS")
            return sdf.format(cal.time)
        }
    }
}
