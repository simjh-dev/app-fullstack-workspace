package com.example.accountbookuisampling.util

import java.lang.NumberFormatException

class CalculatorUtil {


    companion object {
        private val TAG = this::class.java.simpleName

        fun calculate(value: String): String {
            // remove ￥
            val removeEnn = value.substring(1)

            // 연산자가 없는 경우
            return if (getOperators(removeEnn).isEmpty()) removeEnn
            // 연산자 중에 (÷)(×)가 있는 경우
            else if (getDivideOrMultiplication(removeEnn, getOperators(removeEnn)).isNotEmpty()) {
                var result = removeEnn

                // (÷)(×) 연산자가 없어질때까지 반복
                while (getDivideOrMultiplication(result, getOperators(result)).isNotEmpty()) {
                    result = calculateDivideOrMultiplication(result)
                }

                // 모든 연산자가 없어질때까지 반복
                while (getOperators(result).isNotEmpty()) {
                    result = calculateAdditionOrSubtraction(result)
                }

                result
            }
            // 연산자 중에 (÷)(×)가 없는 경우
            else {
                var result = removeEnn
                // 모든 연산자가 없어질때까지 반복
                while (getOperators(result).isNotEmpty()) {
                    result = calculateAdditionOrSubtraction(result)
                }
                result
            }

        }

        // 맨 앞에 있는 (÷)(×) 연산자 찾아 연산
        private fun calculateDivideOrMultiplication(value: String): String {
            val operatorIndexList = getDivideOrMultiplication(value, getOperators(value))
            val headOperatorIndex = operatorIndexList[0]
            val headOperator = value[headOperatorIndex].toString()

            var prevNumberFirstIndex = -1
            // search first index of prev number
            for (i in headOperatorIndex - 1 downTo 0) {
                try {
                    value[i].toString().toInt()
                }
                // 문자가 숫자가 아닌 경우
                catch (e: NumberFormatException) {
                    if (value[i].toString() != TEXT_DOT) {
                        prevNumberFirstIndex = i + 1
                        break
                    }
                }
            }

            var afterNumberLastIndex = -1
            // search last index of after number
            for (i in headOperatorIndex + 1 until value.length) {
                try {
                    value[i].toString().toInt()
                }
                // 문자가 숫자가 아닌 경우
                catch (e: NumberFormatException) {
                    if (value[i].toString() != TEXT_DOT) {
                        afterNumberLastIndex = i
                        break
                    }
                }
            }

            val prevNumber =
                // prev number가 수식의 첫번째 숫자일 경우, 앞에 연산자가 없는 경우
                if (prevNumberFirstIndex == -1) {
                    prevNumberFirstIndex = 0
                    value.substring(0, headOperatorIndex).toDouble()
                }
                // prev number가 수식의 첫번째 숫자가 아닐 경우, 앞에 연산자가 있는 경우
                else {
                    value.substring(prevNumberFirstIndex, headOperatorIndex).toDouble()
                }

            val afterNumber =
                // after number가 수식의 마지막 숫자일 경우, 뒤에 연산자가 없는 경우
                if (afterNumberLastIndex == -1) {
                    afterNumberLastIndex = value.length
                    value.substring(headOperatorIndex + 1).toDouble()
                }
                // after number가 수식의 마지막 숫자가 아닐 경우, 뒤에 연산자가 있는 경우
                else {
                    value.substring(headOperatorIndex + 1, afterNumberLastIndex).toDouble()
                }

            return when (headOperator) {
                TEXT_MULTIPLICATION -> {
                    val calculated = prevNumber * afterNumber
                    val prev = value.substring(0, prevNumberFirstIndex)
                    val after = value.substring(afterNumberLastIndex, value.length)
                    "$prev$calculated$after"
                }
                TEXT_DIVIDE -> {
                    val calculated = prevNumber.toFloat() / afterNumber.toFloat()
                    val prev = value.substring(0, prevNumberFirstIndex)
                    val after = value.substring(afterNumberLastIndex, value.length)
                    "$prev$calculated$after"
                }
                else -> value
            }
        }

        // 맨 앞에 있는 연산자로 연산
        private fun calculateAdditionOrSubtraction(value: String): String {

            val operatorIndexList = getOperators(value)
            val headOperatorIndex = operatorIndexList[0]
            val headOperator = value[headOperatorIndex].toString()

            var effectedAfterIndex = -1

            val prevNumber = value.substring(0, headOperatorIndex).toDouble()
            // 연산자가 하나인 경우
            val afterNumber = if (operatorIndexList.size == 1) {
                effectedAfterIndex = value.length
                value.substring(headOperatorIndex + 1).toDouble()
            }
            // 연산자가 복수인 경우
            else {
                effectedAfterIndex = operatorIndexList[1]
                value.substring(headOperatorIndex + 1, operatorIndexList[1]).toDouble()
            }

            return when (headOperator) {
                TEXT_ADDITION -> {
                    val calculated = prevNumber + afterNumber
                    "$calculated${value.substring(effectedAfterIndex)}"
                }
                TEXT_SUBTRACTION -> {
                    val calculated = prevNumber - afterNumber
                    "$calculated${value.substring(effectedAfterIndex)}"
                }
                else -> value
            }
        }

        fun getOperators(value: String): ArrayList<Int> {
            val result = ArrayList<Int>()
            value.forEachIndexed { index, char ->
                try {
                    char.toString().toInt()
                }
                // 문자가 숫자가 아닌 경우
                catch (e: NumberFormatException) {
                    if (char.toString() != TEXT_DOT) {
                        result.add(index)
                    }
                }
            }
            return result
        }

        fun isDotInLastNotNumberChar(value: String): Boolean {
            val result = ArrayList<Int>()
            value.forEachIndexed { index, char ->
                try {
                    char.toString().toInt()
                }
                // 문자가 숫자가 아닌 경우
                catch (e: NumberFormatException) {
                    result.add(index)
                }
            }
            return value[result[result.size - 1]].toString() == TEXT_DOT
        }


        private fun getDivideOrMultiplication(
            value: String,
            operatorIndexList: ArrayList<Int>
        ): ArrayList<Int> {
            val divideOrMultiplicationIndexList = operatorIndexList.filter { i ->
                value[i].toString() == TEXT_DIVIDE || value[i].toString() == TEXT_MULTIPLICATION
            } as ArrayList<Int>
            return divideOrMultiplicationIndexList
        }
    }

}