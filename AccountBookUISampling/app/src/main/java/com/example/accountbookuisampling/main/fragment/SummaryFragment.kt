package com.example.accountbookuisampling.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.accountbookuisampling.application.BaseApplication
import com.example.accountbookuisampling.databinding.FragmentSummaryBinding
import com.example.accountbookuisampling.main.activity.MainActivity
import com.example.accountbookuisampling.room.dto.Summary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SummaryFragment(private val currentDate: String?) : Fragment() {

    private lateinit var binding: FragmentSummaryBinding
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setData()
    }

    private fun setData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val list =
                (requireActivity().application as BaseApplication).historyDao.getSummaryByYear(
                    currentDate?.substring(0, 4)
                )
            lifecycleScope.launch(Dispatchers.Main) {
                setSummary(list)
            }
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

    companion object {
//        private var instance: SummaryFragment? = null
//
//        @JvmStatic
//        fun getInstance(): SummaryFragment {
//            if (instance == null) {
//                instance = SummaryFragment()
//                return instance as SummaryFragment
//            }
//
//            return instance as SummaryFragment
//        }
    }
}