package com.example.accountbookuisampling.util

import android.graphics.Color
import androidx.room.PrimaryKey
import com.example.accountbookuisampling.main.viewmodel.CalendarViewModel
import com.example.accountbookuisampling.main.viewmodel.DayViewModel
import com.example.accountbookuisampling.main.viewmodel.MonthViewModel
import com.example.accountbookuisampling.main.viewmodel.WeekViewModel
import com.example.accountbookuisampling.registerinput.dataclass.InputDateItem
import com.example.accountbookuisampling.room.entity.Asset
import com.example.accountbookuisampling.room.entity.Category

const val DB_NAME = "AccountBook"

const val MAIN_FRAGMENT_COUNT = 5
const val REGISTER_FRAGMENT_COUNT = 3


const val TITLE_DAY = "일일"
const val TITLE_CALENDAR = "달력"
const val TITLE_WEEKS = "주별"
const val TITLE_MONTHS = "월별"
const val TITLE_SUMMARY = "요약"

const val ID_DAY = 0
const val ID_CALENDAR = 1
const val ID_WEEKS = 2
const val ID_MONTHS = 3
const val ID_SUMMARY = 4

const val TYPE_CALENDAR_HEAD = 0
const val TYPE_CALENDAR_CONTENT = 1

const val INCOME = "수입"
const val CONSUMPTION = "지출"
const val TRANSFER = "이체"

const val FLAG_DATE = 1
const val FLAG_ASSET = 2
const val FLAG_CATEGORY = 3
const val FLAG_AMOUNT = 4
const val FLAG_FEE = 5
const val FLAG_GROUP = 6
const val FLAG_NAME = 7

const val FLAG_INCOME = 0
const val FLAG_CONSUMPTION = 1
const val FLAG_TRANSFER = 2

const val TEXT_SUNDAY = "일"
const val TEXT_MONDAY = "월"
const val TEXT_TUESDAY = "화"
const val TEXT_WEDNESDAY = "수"
const val TEXT_THURSDAY = "목"
const val TEXT_FRIDAY = "금"
const val TEXT_SATURDAY = "토"

const val COLOR_SUNDAY = Color.RED
const val COLOR_WEEKDAY = Color.GRAY
const val COLOR_SATURDAY = Color.BLUE
const val COLOR_INCOME = Color.BLUE

// orange hex : #ce7e00
val COLOR_CONSUMPTION = Color.rgb(206, 126, 0)
val COLOR_ORANGE = Color.rgb(206, 126, 0)
val COLOR_TRANSFER = Color.rgb(0, 0, 0)

const val CALENDAR_HEAD_HEIGHT_RATIO = 20
const val CALENDAR_CONTENT_HEIGHT_RATIO = 5

const val TEXT_ADD = "추가"
const val TEXT_FIRST_DAY_OF_MONTH = "01"
const val TEXT_DATE = "날짜"
const val TEXT_ASSET = "자산"
const val TEXT_CATEGORY = "분류"
const val TEXT_AMOUNT = "금액"

const val CALENDAR_CONTENT_SIZE = 35

val CALENDAR_HEAD_LIST = arrayOf(
    CalendarViewModel(TYPE_CALENDAR_HEAD, "0", "", "", ""),
    CalendarViewModel(TYPE_CALENDAR_HEAD, "1", "", "", ""),
    CalendarViewModel(TYPE_CALENDAR_HEAD, "2", "", "", ""),
    CalendarViewModel(TYPE_CALENDAR_HEAD, "3", "", "", ""),
    CalendarViewModel(TYPE_CALENDAR_HEAD, "4", "", "", ""),
    CalendarViewModel(TYPE_CALENDAR_HEAD, "5", "", "", ""),
    CalendarViewModel(TYPE_CALENDAR_HEAD, "6", "", "", ""),
)
const val FLAG_NOT_CALENDAR_HEAD = -1
const val CALENDAR_VIEW_SPAN_COUNT = 7
const val INPUT_ITEM_VIEW_SPAN_COUNT = 4

const val TAG_DATE = "DATE"
const val TAG_ASSET = "ASSET"
const val TAG_CATEGORY = "CATEGORY"
const val TAG_AMOUNT = "AMOUNT"

const val TEXT_DIVIDE = "÷"
const val TEXT_MULTIPLICATION = "×"
const val TEXT_SUBTRACTION = "－"
const val TEXT_ADDITION = "＋"
const val TEXT_SEVEN = "7"
const val TEXT_EIGHT = "8"
const val TEXT_NINE = "9"
const val TEXT_EQUAL = "＝"
const val TEXT_FOUR = "4"
const val TEXT_FIVE = "5"
const val TEXT_SIX = "6"
const val TEXT_DOT = "."
const val TEXT_ONE = "1"
const val TEXT_TWO = "2"
const val TEXT_THREE = "3"
const val TEXT_REMOVE = "←"
const val TEXT_EMPTY = ""
const val TEXT_ZERO = "0"
const val TEXT_CONFIRM = "확인"
const val TEXT_MONTHS = "개월"
const val TEXT_INSTALLMENT = "할부"

const val TEXT_CURRENT_VIEW = "currentView"
const val TEXT_AM = "오전"
const val TEXT_PM = "오후"
const val TEXT_FLAG = "flag"
const val TEXT_ITEM = "item"

val CALCULATOR_ITEM_LIST = arrayOf(
    TEXT_DIVIDE,
    TEXT_MULTIPLICATION,
    TEXT_SUBTRACTION,
    TEXT_ADDITION,
    TEXT_SEVEN,
    TEXT_EIGHT,
    TEXT_NINE,
    TEXT_EQUAL,
    TEXT_FOUR,
    TEXT_FIVE,
    TEXT_SIX,
    TEXT_DOT,
    TEXT_ONE,
    TEXT_TWO,
    TEXT_THREE,
    TEXT_REMOVE,
    TEXT_EMPTY,
    TEXT_ZERO,
    TEXT_EMPTY,
    TEXT_CONFIRM
)

const val TEXT_INIT = "init"
const val TEXT_NONE = "없음"
const val TEXT_EVERY_DAY = "매일"
const val TEXT_EVERY_WEEK = "매주"
const val TEXT_EVERY_TWO_WEEKS = "2주마다"
const val TEXT_EVERY_FOUR_WEEKS = "4주마다"
const val TEXT_EVERY_MONTH = "매월"
const val TEXT_THE_END_OF_THE_MONTH = "월말"
const val TEXT_EVERY_TWO_MONTHS = "2개월마다"
const val TEXT_EVERY_THREE_MONTHS = "3개월마다"
const val TEXT_EVERY_FOUR_MONTHS = "4개월마다"
const val TEXT_EVERY_SIX_MONTHS = "6개월마다"
const val TEXT_EVERY_YEAR = "1년마다"

val REPEAT_ITEM_LIST = arrayOf(
    TEXT_NONE,
    TEXT_EVERY_DAY,
    TEXT_EVERY_WEEK,
    TEXT_EVERY_TWO_WEEKS,
    TEXT_EVERY_FOUR_WEEKS,
    TEXT_EVERY_MONTH,
    TEXT_THE_END_OF_THE_MONTH,
    TEXT_EVERY_TWO_MONTHS,
    TEXT_EVERY_THREE_MONTHS,
    TEXT_EVERY_FOUR_MONTHS,
    TEXT_EVERY_SIX_MONTHS,
    TEXT_EVERY_YEAR
)

const val TEXT_CLEAR = "C"
const val TEXT_COMPLETE = "완료"
val NUMBER_PLATE_LIST = arrayOf(
    TEXT_ONE,
    TEXT_TWO,
    TEXT_THREE,
    TEXT_REMOVE,
    TEXT_FOUR,
    TEXT_FIVE,
    TEXT_SIX,
    TEXT_CLEAR,
    TEXT_SEVEN,
    TEXT_EIGHT,
    TEXT_NINE,
    TEXT_EMPTY,
    TEXT_EMPTY,
    TEXT_ZERO,
    TEXT_EMPTY,
    TEXT_COMPLETE
)

const val TEXT_ENN = "￥"
const val TEXT_MONTH = "월"
const val TEXT_ALPHA = "alpha"

const val SWIPE_THRESHOLD = 50
const val SWIPE_VELOCITY_THRESHOLD = 100
const val DURATION_ALPHA = 500L

const val TEXT_CURRENT_ITEM = "CURRENT_ITEM"


val MONTH_VIEW_MODEL_LIST = arrayListOf<MonthViewModel>(
    MonthViewModel(
        "",
        "",
        "0",
        "0",
        "0",
    ),
    MonthViewModel(
        "",
        "",
        "0",
        "0",
        "0",
    ),
    MonthViewModel(
        "",
        "",
        "0",
        "0",
        "0",
    ),
    MonthViewModel(
        "",
        "",
        "0",
        "0",
        "0",
    ),
    MonthViewModel(
        "",
        "",
        "0",
        "0",
        "0",
    ),
    MonthViewModel(
        "",
        "",
        "0",
        "0",
        "0",
    ),
    MonthViewModel(
        "",
        "",
        "0",
        "0",
        "0",
    ),
    MonthViewModel(
        "",
        "",
        "0",
        "0",
        "0",
    ),
    MonthViewModel(
        "",
        "",
        "0",
        "0",
        "0",
    ),
    MonthViewModel(
        "",
        "",
        "0",
        "0",
        "0",
    ),
    MonthViewModel(
        "",
        "",
        "0",
        "0",
        "0",
    ),
    MonthViewModel(
        "",
        "",
        "0",
        "0",
        "0",
    ),
)

val TEMP_DAY_VIEW_MODEL_LIST = arrayListOf<DayViewModel>(
    DayViewModel(
        1,
        "10",
        "2212",
        "토",
        "M카드",
        "숙소비",
        "오후 05:01",
        0,  // income
        "10000",
    ),
    DayViewModel(
        2,
        "10",
        "2212",
        "토",
        "M카드",
        "숙소비",
        "오후 05:02",
        1,  // consumption
        "10000",
    ),
    DayViewModel(
        3,
        "10",
        "2212",
        "토",
        "M카드",
        "숙소비",
        "오전 07:10",
        0,  // income
        "10000",
    ),
)

val TEMP_WEEK_VIEW_MODEL_LIST = arrayListOf<WeekViewModel>(
    WeekViewModel(
        "11/28~12/04",
        "100",
        "200",
        "-100"
    ),
    WeekViewModel(
        "12/05~12/11",
        "100",
        "200",
        "-100"
    ),
    WeekViewModel(
        "12/12~12/18",
        "100",
        "200",
        "-100"
    ),
    WeekViewModel(
        "12/19~12/25",
        "100",
        "200",
        "-100"
    ),
    WeekViewModel(
        "12/26~01/01",
        "100",
        "200",
        "-100"
    ),
)

val INPUT_DATE_HEAD_LIST = arrayOf(
    InputDateItem(TYPE_CALENDAR_HEAD, 0, ""),
    InputDateItem(TYPE_CALENDAR_HEAD, 1, ""),
    InputDateItem(TYPE_CALENDAR_HEAD, 2, ""),
    InputDateItem(TYPE_CALENDAR_HEAD, 3, ""),
    InputDateItem
        (TYPE_CALENDAR_HEAD, 4, ""),
    InputDateItem(TYPE_CALENDAR_HEAD, 5, ""),
    InputDateItem(TYPE_CALENDAR_HEAD, 6, "")
)

const val TEXT_GROUP_ID = "groupId"
const val TEXT_GROUP = "group"
const val TEXT_NAME = "name"
const val TEXT_MEMO = "memo"
const val TEXT_RESULT = "result"
const val TEXT_CLOSE = "close"
const val TEXT_CLICK = "click"

val INIT_INCOME_ASSETS = arrayOf(
    "현금",
    "카드",
    "은행",
    "기타",
)
val INIT_CONSUMPTION_ASSETS = arrayOf(
    "현금",
    "카드",
    "은행",
    "기타"
)
val INIT_TRANSFER_ASSETS = arrayOf(
    "현금",
    "신한은행",
    "미즈호",
    "해외송금",
    "기타",
)

val INIT_INCOME_CATEGORIES = arrayOf(
    "월급",
    "상여",
    "환급",
    "이자",
    "기타",
)
val INIT_CONSUMPTION_CATEGORIES = arrayOf(
    "식비",
    "교통비",
    "공과금",
    "경조사",
    "옷",
    "사치",
    "적금",
    "기타"
)
val INIT_TRANSFER_CATEGORIES = arrayOf(
    "부모님",
    "은행간이동",
    "해외송금",
    "기타",
)