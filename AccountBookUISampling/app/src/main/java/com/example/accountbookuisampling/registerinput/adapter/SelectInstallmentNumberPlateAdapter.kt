package com.example.accountbookuisampling.registerinput.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.databinding.*
import com.example.accountbookuisampling.util.*

class SelectInstallmentNumberPlateAdapter(
    numberPlateList: Array<String>,
    private val parentBinding: ActivitySelectInstallmentBinding
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = numberPlateList
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemNumberPlateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NumberPlateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as NumberPlateViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class NumberPlateViewHolder(private val binding: RvItemNumberPlateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            if (item == TEXT_EMPTY) {
                binding.btnInput.isEnabled = false
            }
            binding.btnInput.text = item
            binding.btnInput.setOnClickListener {
                val prev = parentBinding.tvResult.text.toString()
                var value = TEXT_EMPTY
                // 처음 입력의 경우
                if (prev != TEXT_EMPTY) {
                    value = prev
                }

                // 숫자 경우(0,1,2,3,4,5,6,7,8,9)
                if (item == TEXT_ZERO || item == TEXT_ONE || item == TEXT_TWO || item == TEXT_THREE || item == TEXT_FOUR || item == TEXT_FIVE || item == TEXT_SIX || item == TEXT_SEVEN || item == TEXT_EIGHT || item == TEXT_NINE) {
                    val index = getMonthIndex(value)
                    value = if(index == -1) {
                        "$item$TEXT_MONTHS"
                    } else {
                        val prevMonths = value.substring(0, index)
                        "$prevMonths$item$TEXT_MONTHS"
                    }
                } else if (item == TEXT_REMOVE) {
                    val index = getMonthIndex(value)
                    value = if(index == -1 || index == 1) {
                        TEXT_EMPTY
                    } else {
                        val removeLastNumber = value.substring(0, index-1)
                        "$removeLastNumber$TEXT_MONTHS"
                    }
                } else if (item == TEXT_CLEAR) {
                    value = TEXT_EMPTY
                } else if (item == TEXT_COMPLETE) {
                    parentBinding.btnComplete.performClick()
                    return@setOnClickListener
                }
                parentBinding.tvResult.text = value
            }
        }

        private fun getMonthIndex(value: String) : Int {
            return value.indexOf(TEXT_MONTHS)
        }
    }
}