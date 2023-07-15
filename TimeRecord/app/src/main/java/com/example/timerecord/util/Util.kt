package com.example.timerecord.util

import com.example.timerecord.util.Const.TYPE_CALENDAR_CONTENT_INVISIBLE
import com.example.timerecord.util.Const.TYPE_CALENDAR_CONTENT_VISIBLE
import com.example.timerecord.viewmodel.TodoHistoryViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class Util {

    companion object {

        fun fillTodoHistoryViewModelList(selectedDate: String, input: List<TodoHistoryViewModel>): ArrayList<TodoHistoryViewModel> {
            val c = Calendar.getInstance()
            c.time = SimpleDateFormat("yyyyMMdd").parse(selectedDate)
            val currentMM = SimpleDateFormat("MM").format(c.time)

            c.set(Calendar.DAY_OF_MONTH, 1)
            val firstDayOfWeekWithCalendar = c.get(Calendar.DAY_OF_WEEK)
            // (row,col):(1,1)
            c.set(
                Calendar.DAY_OF_MONTH,
                c.get(Calendar.DAY_OF_MONTH) - (firstDayOfWeekWithCalendar - 1)
            )
            val sdf = SimpleDateFormat("yyyyMMdd")
            val result = arrayListOf<TodoHistoryViewModel>()
            for (i in 0 until 35) {
                val yyyyMMdd = sdf.format(c.time)
                val MM = SimpleDateFormat("MM").format(c.time)
                val filter = input.filter { item -> item.targetDate == yyyyMMdd }

                if (currentMM != MM) {
                    result.add(
                        TodoHistoryViewModel(
                            0,
                            0,
                            "",
                            "",
                            "",
                            TYPE_CALENDAR_CONTENT_INVISIBLE,
                            ""
                        )
                    )
                } else if (filter.isEmpty()) {
                    result.add(
                        TodoHistoryViewModel(
                            0,
                            0,
                            yyyyMMdd,
                            "",
                            "",
                            TYPE_CALENDAR_CONTENT_VISIBLE,
                            ""
                        )
                    )
                } else {
                    result.add(
                        filter[0]
                    )
                }
                c.add(Calendar.DAY_OF_MONTH, 1)
            }
            return result
        }

        fun getToday(): String = SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().time)
        fun getFormattedToday(): String =
            SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().time)

        fun getCurrentTime(): String = SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().time)

        fun isValidateToTime(value: String?): Boolean {
            if (value == null) return true
            try {
                value.toInt()
            } catch (e: Exception) {
                return false
            }
            if (value.length != 4) return false
            if (value.substring(0, 2).toInt() !in 0..23) return false
            if (value.substring(2, 4).toInt() !in 0..59) return false
            return true
        }
    }

}