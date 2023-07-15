package com.example.accountbookuisampling.main.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.main.activity.MainActivity
import com.example.accountbookuisampling.main.adapter.CalendarRVAdapter
import com.example.accountbookuisampling.application.BaseApplication
import com.example.accountbookuisampling.databinding.FragmentCalendarBinding
import com.example.accountbookuisampling.util.*
import com.example.accountbookuisampling.main.viewmodel.CalendarViewModel
import com.example.accountbookuisampling.room.dto.Summary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class CalendarFragment(private val currentDate: String?) : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private val TAG = this::class.java.simpleName

    private val _list = ArrayList<CalendarViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        setData()
    }

    private fun setData() {
        val dateList = DateUtil.getDateList(currentDate?.substring(0, 6))
        val firstDate = dateList[0]
        val lastDate = dateList[dateList.size - 1]
        lifecycleScope.launch(Dispatchers.IO) {
            val list =
                (requireActivity().application as BaseApplication).historyDao.getSummaryByPeriod(
                    firstDate,
                    lastDate
                )
            lifecycleScope.launch(Dispatchers.Main) {
                setCalendarItems(list, dateList)
                setSummary(list)
                setRecyclerView()

//                (requireActivity() as MainActivity).updateSummary(1500, 3000, 1500 - 3000)
            }
        }
    }

    private fun setCalendarItems(list: List<Summary>, dateList: List<String>) {
        for (date in dateList) {
            val filter = list.filter { summary -> summary.date?.substring(0, 8) == date }
            addItem(filter, date.substring(0, 8))
        }
    }

    private fun setRecyclerView() {
        val list = ArrayList<CalendarViewModel>()
        if (_list.isEmpty()) {
            list.addAll(CALENDAR_HEAD_LIST)
            list.addAll(getEmptyContentList())
        } else {
            list.addAll(CALENDAR_HEAD_LIST)
            list.addAll(_list)
        }

        setLayoutManager()
        // waiting refresh for measuredHeight is 0
        binding.rvList.post {
            binding.rvList.minimumHeight = binding.rvList.measuredHeight
            binding.rvList.adapter = CalendarRVAdapter(list)
        }
    }

    private fun setSummary(summaries: List<Summary>) {
        var income = 0
        var consumption = 0
        var transfer = 0

        for (summary in summaries) {
            when (summary.type) {
                // income
                0 -> {
                    income += summary.result
                }
                // consumption
                1 -> {
                    consumption += summary.result
                }
                // transfer
                2 -> {
                    transfer += summary.result
                }
            }
        }
        val sum = income - consumption
        (requireActivity() as MainActivity).updateSummary(income, consumption, sum)
    }

    private fun setLayoutManager() {
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(context, CALENDAR_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    GridLayoutManager.HORIZONTAL
                binding.rvList.layoutManager = layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(context, CALENDAR_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                binding.rvList.layoutManager = layoutManager
            }
            else -> throw NotImplementedError()
        }
    }

    private fun getEmptyContentList(): ArrayList<CalendarViewModel> {
        val list = ArrayList<CalendarViewModel>()
        val currentYearMonth = currentDate?.substring(0, 6)
        val dateList = DateUtil.getDateList(currentYearMonth)

        dateList.forEach { date ->
            list.add(
                CalendarViewModel(
                    TYPE_CALENDAR_CONTENT,
                    date,
                    "",
                    "",
                    ""
                )
            )
        }

        return list
    }

    private fun addItem(summaries: List<Summary>, date: String) {
        var income = 0
        var consumption = 0
        var transfer = 0
        for (summary in summaries) {
            when (summary.type) {
                // income
                0 -> {
                    income = summary.result
                }
                // consumption
                1 -> {
                    consumption = summary.result
                }
                // transfer
                2 -> {
                    transfer = summary.result
                }
            }
        }

        val sum = income - consumption

        _list.add(
            CalendarViewModel(
                TYPE_CALENDAR_CONTENT,
                date,
                consumption.toString(),
                income.toString(),
                sum.toString()
            )
        )
    }


    companion object {
//        private var instance: CalendarFragment? = null
//        @JvmStatic
//        fun getInstance() : CalendarFragment {
//            if(instance == null) {
//                instance = CalendarFragment()
//                return instance as CalendarFragment
//            }
//
//            return instance as CalendarFragment
//        }
    }
}