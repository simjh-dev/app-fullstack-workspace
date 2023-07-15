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
import com.example.accountbookuisampling.main.adapter.WeekRVAdapter
import com.example.accountbookuisampling.application.BaseApplication
import com.example.accountbookuisampling.databinding.FragmentWeekBinding
import com.example.accountbookuisampling.main.activity.MainActivity
import com.example.accountbookuisampling.util.TEMP_WEEK_VIEW_MODEL_LIST
import com.example.accountbookuisampling.main.viewmodel.WeekViewModel
import com.example.accountbookuisampling.room.dto.Summary
import com.example.accountbookuisampling.util.DateUtil
import com.example.accountbookuisampling.util.TYPE_CALENDAR_CONTENT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class WeekFragment(private val currentDate: String?) : Fragment() {

    private lateinit var binding: FragmentWeekBinding
    private val TAG = this::class.java.simpleName
    private val _list = ArrayList<WeekViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeekBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setData()
    }

    private fun setData() {
        val dateList = DateUtil.getDateList(currentDate?.substring(0, 6))
        val weekCount = dateList.size / 7
        lifecycleScope.launch(Dispatchers.IO) {
            for (i in 0 until weekCount) {
                // 毎週月曜日
                val mondayDate = dateList[i * 7 + 1]
                val sundayDate = DateUtil.getSundayByMonday(mondayDate)

                val list =
                    (requireActivity().application as BaseApplication).historyDao.getSummaryByPeriod(
                        mondayDate,
                        sundayDate
                    )
                lifecycleScope.launch(Dispatchers.Main) {
                    addWeekItem(list, mondayDate, sundayDate)
                    // if add last item
                    if (i == weekCount - 1) {
                        setSummary()
                        setRecyclerView()
                    }
                }
            }
        }
    }

    private fun addWeekItem(summaries: List<Summary>, monday: String, sunday: String) {
        val period = "$monday~$sunday"

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

        var sum = income - consumption

        _list.add(
            WeekViewModel(
                period,
                income.toString(),
                consumption.toString(),
                sum.toString()
            )
        )
    }

    private fun setSummary() {
        var income = 0
        var consumption = 0

        for (item in _list) {
            income += item.income.toInt()
            consumption += item.consumption.toInt()
        }

        val sum = income - consumption
        (requireActivity() as MainActivity).updateSummary(income, consumption, sum)
    }

    private fun setRecyclerView() {
        setLayoutManager()

        val list = ArrayList<WeekViewModel>()
        if (_list.isEmpty()) {
            list.addAll(TEMP_WEEK_VIEW_MODEL_LIST)
        } else {
            list.addAll(_list)
        }
        binding.rvList.adapter = WeekRVAdapter(list)
    }

    private fun setLayoutManager() {
        val layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                GridLayoutManager(context, 2)
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                LinearLayoutManager(context)
            }
            else -> throw NotImplementedError()
        }
        binding.rvList.layoutManager = layoutManager
    }

    companion object {
//        private var instance: WeekFragment? = null
//        @JvmStatic
//        fun getInstance() : WeekFragment {
//            if(instance == null) {
//                instance = WeekFragment()
//                return instance as WeekFragment
//            }
//
//            return instance as WeekFragment
//        }
    }

}