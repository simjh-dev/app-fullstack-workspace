package com.example.timerecord.util

import com.example.timerecord.entity.Todo
import com.example.timerecord.entity.TodoHistory
import com.example.timerecord.viewmodel.TodoHistoryViewModel

object Const {
    val TEMP_TODO_LIST = listOf(
        Todo(
            0,
            "Company",
            "20230331",
            "0900",
            "1800",
            listOf(true, true, true, true, true, false, false)
        ),
        Todo(
            1,
            "Gym",
            "20230225",
            "1900",
            "2030",
            listOf(false, false, false, false, false, true, true)
        ),
        Todo(
            1,
            "Study",
            "20230115",
            "0600",
            "0700",
            listOf(true, true, true, true, true, true, true)
        )
    )

    val TEMP_TODO_HISTORY_LIST = listOf(
        TodoHistory(0, 0, "20230327", "0855", "1803"),
        TodoHistory(0, 0, "20230328", "0855", "1803"),
        TodoHistory(0, 0, "20230329", "0855", "1803"),
        TodoHistory(0, 0, "20230330", "0855", "1803"),
        TodoHistory(0, 0, "20230331", "0855", "1803"),
    )

    val TEMP_TODO_HISTORY_HEAD_LIST = listOf(
        TodoHistoryViewModel(
            0,
            0,
            "",
            "",
            "",
            0, // TYPE_CALENDAR_HEAD
            "SUN"
        ),
        TodoHistoryViewModel(
            0,
            0,
            "",
            "",
            "",
            0, // TYPE_CALENDAR_HEAD
            "MON"
        ),
        TodoHistoryViewModel(
            0,
            0,
            "",
            "",
            "",
            0, // TYPE_CALENDAR_HEAD
            "TUE"
        ),
        TodoHistoryViewModel(
            0,
            0,
            "",
            "",
            "",
            0, // TYPE_CALENDAR_HEAD
            "WED"
        ),
        TodoHistoryViewModel(
            0,
            0,
            "",
            "",
            "",
            0, // TYPE_CALENDAR_HEAD
            "THU"
        ),
        TodoHistoryViewModel(
            0,
            0,
            "",
            "",
            "",
            0, // TYPE_CALENDAR_HEAD
            "FRI"
        ),
        TodoHistoryViewModel(
            0,
            0,
            "",
            "",
            "",
            0, // TYPE_CALENDAR_HEAD
            "SAT"
        )
    )

    val TEMP_TODO_HISTORY_CONTENT_LIST = listOf(
        TodoHistoryViewModel(
            0,
            1,
            "20230403",
            "0900",
            "1800",
            1, // TYPE_CALENDAR_CONTENT
            ""
        ),
        TodoHistoryViewModel(
            0,
            1,
            "20230404",
            "0900",
            "1800",
            1, // TYPE_CALENDAR_CONTENT
            ""
        ),
        TodoHistoryViewModel(
            0,
            1,
            "20230405",
            "0900",
            "1800",
            1, // TYPE_CALENDAR_CONTENT
            ""
        ),
        TodoHistoryViewModel(
            0,
            1,
            "20230406",
            "0900",
            "1800",
            1, // TYPE_CALENDAR_CONTENT
            ""
        ),
        TodoHistoryViewModel(
            0,
            1,
            "20230407",
            "0900",
            "1800",
            1, // TYPE_CALENDAR_CONTENT
            ""
        ),
        TodoHistoryViewModel(
            0,
            1,
            "20230410",
            "0900",
            "1800",
            1, // TYPE_CALENDAR_CONTENT
            ""
        ),
        TodoHistoryViewModel(
            0,
            1,
            "20230411",
            "0900",
            "1800",
            1, // TYPE_CALENDAR_CONTENT
            ""
        ),
        TodoHistoryViewModel(
            0,
            1,
            "20230412",
            "0900",
            "1800",
            1, // TYPE_CALENDAR_CONTENT
            ""
        ),
        TodoHistoryViewModel(
            0,
            1,
            "20230413",
            "0900",
            "1800",
            1, // TYPE_CALENDAR_CONTENT
            ""
        ),
        TodoHistoryViewModel(
            0,
            1,
            "20230414",
            "0900",
            "1800",
            1, // TYPE_CALENDAR_CONTENT
            ""
        ),
        TodoHistoryViewModel(
            0,
            1,
            "20230417",
            "0900",
            "1800",
            1, // TYPE_CALENDAR_CONTENT
            ""
        ),
        TodoHistoryViewModel(
            0,
            1,
            "20230418",
            "0900",
            "1800",
            1, // TYPE_CALENDAR_CONTENT
            ""
        ),
        TodoHistoryViewModel(
            0,
            1,
            "20230419",
            "0900",
            "1800",
            1, // TYPE_CALENDAR_CONTENT
            ""
        ),
        TodoHistoryViewModel(
            0,
            1,
            "20230420",
            "0900",
            "1800",
            1, // TYPE_CALENDAR_CONTENT
            ""
        ),
        TodoHistoryViewModel(
            0,
            1,
            "20230421",
            "0900",
            "1800",
            1, // TYPE_CALENDAR_CONTENT
            ""
        ),
    )

    const val DELAY_SHOW_UI = 300L
    const val MORE_DELAY_SHOW_UI = 1000L
    const val DELAY_ONE_SECONDS = 1000L

    const val TYPE_CALENDAR_HEAD = 0
    const val TYPE_CALENDAR_CONTENT_VISIBLE = 1
    const val TYPE_CALENDAR_CONTENT_INVISIBLE = 2
    const val FLAG_START_TIME = 1
    const val FLAG_END_TIME = 2
}