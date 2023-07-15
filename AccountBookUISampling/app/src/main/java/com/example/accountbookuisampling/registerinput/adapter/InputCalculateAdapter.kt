package com.example.accountbookuisampling.registerinput.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.databinding.RvItemCalculateInputBinding
import com.example.accountbookuisampling.registerinput.fragment.BaseRegisterInputFragment
import com.example.accountbookuisampling.util.*
import kotlin.math.roundToInt

class InputCalculateAdapter(
    private val list: Array<String>,
    private val fragment: BaseRegisterInputFragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemCalculateInputBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CalculateInputViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as CalculateInputViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class CalculateInputViewHolder(private val binding: RvItemCalculateInputBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            if (item == TEXT_EMPTY) {
                binding.btnInput.isEnabled = false
            }

            binding.btnInput.text = item
            binding.btnInput.setOnClickListener {
                val prev = fragment.getInputAmountValue()
                var value = TEXT_EMPTY
                // 처음 입력의 경우
                if (prev == TEXT_EMPTY) {
                    value = TEXT_ENN
                }

                // 숫자 경우(0,1,2,3,4,5,6,7,8,9)
                if (item == TEXT_ZERO || item == TEXT_ONE || item == TEXT_TWO || item == TEXT_THREE || item == TEXT_FOUR || item == TEXT_FIVE || item == TEXT_SIX || item == TEXT_SEVEN || item == TEXT_EIGHT || item == TEXT_NINE) {
                    value += "$prev$item"
                }
                // 사칙연산 경우(÷,×,－,＋)
                else if (item == TEXT_DIVIDE || item == TEXT_MULTIPLICATION || item == TEXT_ADDITION || item == TEXT_SUBTRACTION) {
                    try {
                        val lastChar = prev.last().toString()
                        // 이전 문자가 숫자인 경우 = exception 발생X
                        lastChar.toInt()
                        value = "$prev$item"
                    }
                    // null인 경우
                    catch (e: NoSuchElementException) {
                        Log.e(TAG, "etInput is NULL")
                    }
                    // 이전 문자가 숫자가 아닌 경우
                    catch (e: NumberFormatException) {
                        Log.e(TAG, "prev last char is not Number")
                        value = prev
                    }
                }
                // 등호 경우(=)
                else if (item == TEXT_EQUAL) {
                    try {
                        val lastChar = prev.last().toString()
                        // 이전 문자가 숫자인 경우 = exception 발생X
                        lastChar.toInt()
                        val calculated = CalculatorUtil.calculate(prev)
                        val rounded = calculated.toDouble().roundToInt()
                        value = "$TEXT_ENN$rounded"
                    }
                    // null인 경우
                    catch (e: NoSuchElementException) {
                        Log.e(TAG, "etInput is NULL")
                        value = TEXT_ENN
                    }
                    // 이전 문자가 숫자가 아닌 경우
                    // 연산 수행을 막음
                    catch (e: NumberFormatException) {
                        Log.e(TAG, "prev last char is not Number")
                        value = prev
                    }
                }
                // 소수점 경우(.)
                else if (item == TEXT_DOT) {
                    try {
                        val lastChar = prev.last().toString()
                        // 이전 문자가 숫자인 경우 = exception 발생X
                        lastChar.toInt()

                        // 이전 문자열에 맨 앞
                        value = if (CalculatorUtil.isDotInLastNotNumberChar(prev)) {
                            prev
                        } else {
                            "$prev$item"
                        }
                    }
                    // null인 경우
                    catch (e: NoSuchElementException) {
                        Log.e(TAG, "etInput is NULL")
                        value = TEXT_ENN
                    }
                    // 이전 문자가 숫자가 아닌 경우
                    // 소수점 추가 막음
                    catch (e: NumberFormatException) {
                        Log.e(TAG, "prev last char is not Number")
                        value = prev
                    }
                }
                // 삭제 경우(←)
                else if (item == TEXT_REMOVE) {
                    value = if (prev == TEXT_EMPTY || prev == TEXT_ENN) {
                        TEXT_ENN
                    } else {
                        prev.substring(0, prev.length - 1)
                    }
                }
                // 확인 경우(확인)
                else if (item == TEXT_CONFIRM) {

                    var result =
                        // 연산이 다 된 경우
                        if (CalculatorUtil.getOperators(prev).isEmpty()) {
                            prev
                        }
                        // 연산이 덜 된 경우
                        else {
                            val calculated = CalculatorUtil.calculate(prev)
                            try {
                                val rounded = calculated.toDouble().roundToInt().toString()
                                rounded
                            } catch (e: NumberFormatException) {
                                TEXT_ENN
                            }
                        }

                    if(result == TEXT_ENN) result = TEXT_EMPTY
                    else if (result.isNotEmpty() && result[0].toString() != TEXT_ENN) result =
                        "$TEXT_ENN$result"
                    fragment.confirmInputAmount(result)
                }
                // TEXT_EMPTY 클릭 혹은 이외의 경우
                else {
                    // 처음 입력의 경우
                    value = if (prev == TEXT_EMPTY) {
                        TEXT_ENN
                    } else {
                        prev
                    }
                }
                fragment.changeInputAmount(value)
            }
        }
    }

}