package com.example.accountbookuisampling.main.fragment

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.main.activity.MainActivity
import com.example.accountbookuisampling.main.adapter.DayRVAdapter
import com.example.accountbookuisampling.application.BaseApplication
import com.example.accountbookuisampling.databinding.FragmentDayBinding
import com.example.accountbookuisampling.util.TEMP_DAY_VIEW_MODEL_LIST
import com.example.accountbookuisampling.main.viewmodel.DayViewModel
import com.example.accountbookuisampling.room.dto.Summary
import com.example.accountbookuisampling.room.entity.History
import com.example.accountbookuisampling.util.DateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DayFragment(private val currentDate: String?) : Fragment() {

    private lateinit var binding: FragmentDayBinding
    private val TAG = this::class.java.simpleName
    private val _list = ArrayList<DayViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setData()
        setClickEvent()
    }

    private fun setData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val list =
                (requireActivity().application as BaseApplication).historyDao.getByDate(currentDate)
            val summary =
                (requireActivity().application as BaseApplication).historyDao.getSummaryByDate(
                    currentDate
                )
            lifecycleScope.launch(Dispatchers.Main) {
                setHistories(list)
                setSummary(summary)
                setRecyclerView()
            }
        }
    }

    private fun setRecyclerView() {
        if (_list.isEmpty()) {
            binding.rvList.visibility = View.GONE
            binding.layoutEmpty.visibility = View.VISIBLE
            return
        }
        setLayoutManager()
        binding.rvList.adapter = DayRVAdapter(_list)
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

    private fun setClickEvent() {

    }

    private fun setHistories(list: List<History>) {
        // convert Entity to ViewModel
        list.forEach { history ->
            val day = history.date.substring(6, 8)
            val yearMonth = history.date.substring(0, 6)
            val time = history.date.substring(8, 12)
            _list.add(
                DayViewModel(
                    history.id,
                    day,
                    yearMonth,
                    DateUtil.getStringDayOfWeek(history.date),
                    history.assetName,
                    history.categoryName,
                    DateUtil.getStringTime(time),
                    history.type,
                    history.amount.toString()
                )
            )
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
        (requireActivity() as MainActivity).updateSummary(income, consumption, sum)

    }

    companion object {
//        private var instance: DayFragment? = null
//        @JvmStatic
//        fun getInstance() : DayFragment {
//            if(instance == null) {
//                instance = DayFragment()
//                return instance as DayFragment
//            }
//
//            return instance as DayFragment
//        }
    }
}