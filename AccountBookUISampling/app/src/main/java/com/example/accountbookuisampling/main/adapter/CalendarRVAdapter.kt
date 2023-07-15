package com.example.accountbookuisampling.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.databinding.RvItemCalendarContentBinding
import com.example.accountbookuisampling.databinding.RvItemCalendarHeadBinding
import com.example.accountbookuisampling.main.viewmodel.CalendarViewModel
import com.example.accountbookuisampling.util.*
import java.text.DecimalFormat
import java.util.ArrayList

class CalendarRVAdapter(private val list: ArrayList<CalendarViewModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val headHeight = parent.measuredHeight / CALENDAR_HEAD_HEIGHT_RATIO
        val contentHeight = (parent.measuredHeight - headHeight) / CALENDAR_CONTENT_HEIGHT_RATIO

        when (viewType) {
            TYPE_CALENDAR_HEAD -> {
                val binding = RvItemCalendarHeadBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                binding.root.layoutParams.height = headHeight
                return HeadViewHolder(binding)
            }
            TYPE_CALENDAR_CONTENT -> {
                val binding = RvItemCalendarContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                binding.root.layoutParams.height = contentHeight
                return ContentViewHolder(binding)
            }
            else -> {
                throw NotImplementedError()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]

        when (item.itemType) {
            TYPE_CALENDAR_HEAD -> {
                (holder as HeadViewHolder).bind(item)
            }
            TYPE_CALENDAR_CONTENT -> {
                (holder as ContentViewHolder).bind(item)
            }
            else -> throw NotImplementedError()
        }

    }

    override fun getItemCount(): Int {
        return _list.size
    }

    override fun getItemViewType(position: Int): Int {
        return _list[position].itemType
    }

    inner class HeadViewHolder(private val binding: RvItemCalendarHeadBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CalendarViewModel) {
            when (item.date) {
                TEXT_ZERO -> {
                    binding.strDayOfWeek = TEXT_SUNDAY
                    binding.tvDayOfWeek.setTextColor(COLOR_SUNDAY)
                }
                TEXT_ONE -> {
                    binding.strDayOfWeek = TEXT_MONDAY
                    binding.tvDayOfWeek.setTextColor(COLOR_WEEKDAY)
                }
                TEXT_TWO -> {
                    binding.strDayOfWeek = TEXT_TUESDAY
                    binding.tvDayOfWeek.setTextColor(COLOR_WEEKDAY)
                }
                TEXT_THREE -> {
                    binding.strDayOfWeek = TEXT_WEDNESDAY
                    binding.tvDayOfWeek.setTextColor(COLOR_WEEKDAY)
                }
                TEXT_FOUR -> {
                    binding.strDayOfWeek = TEXT_THURSDAY
                    binding.tvDayOfWeek.setTextColor(COLOR_WEEKDAY)
                }
                TEXT_FIVE -> {
                    binding.strDayOfWeek = TEXT_FRIDAY
                    binding.tvDayOfWeek.setTextColor(COLOR_WEEKDAY)
                }
                TEXT_SIX -> {
                    binding.strDayOfWeek = TEXT_SATURDAY
                    binding.tvDayOfWeek.setTextColor(COLOR_SATURDAY)
                }
                else -> {
                    binding.strDayOfWeek = TEXT_EMPTY
                    binding.tvDayOfWeek.setTextColor(COLOR_WEEKDAY)
                }
            }
        }
    }

    inner class ContentViewHolder(private val binding: RvItemCalendarContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CalendarViewModel) {
            val model = formatToViewModel(item)
            binding.model = model
        }

        private fun formatToViewModel(item: CalendarViewModel): CalendarViewModel {
            // date = yyyyMMdd
            var day = item.date.substring(6, 8)
            if (day == TEXT_FIRST_DAY_OF_MONTH) {
                val month = item.date.substring(4, 6)
                day = "$month.$day"
            }

            val df = DecimalFormat("#,###")
            return CalendarViewModel(
                TYPE_CALENDAR_CONTENT,
                day,
                if(item.consumption.isBlank()) "" else df.format(item.consumption.toInt()),
                if(item.income.isBlank()) "" else df.format(item.income.toInt()),
                if(item.sum.isBlank()) "" else df.format(item.sum.toInt()),
            )
        }
    }

}