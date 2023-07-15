package com.example.accountbookuisampling.main.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import com.example.accountbookuisampling.register.activity.RegisterActivity
import com.example.accountbookuisampling.databinding.ActivityMainBinding
import com.example.accountbookuisampling.util.OnSwipeTouchListener
import com.example.accountbookuisampling.main.fragment.*
import com.example.accountbookuisampling.util.*
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName
    private var currentDate: String? = null
    private lateinit var onSwipeTouchListener: OnSwipeTouchListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()
        initDate()
        initTabLayout()
        initFrameLayout()
        setClickEvent()
    }


    private fun initDate() {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyyMMdd")
        currentDate = sdf.format(calendar.time)

        val yyyy = currentDate?.substring(0, 4)
        val MM = currentDate?.substring(4, 6)
        val dd = currentDate?.substring(6, 8)
        binding.tvDate.text = "${yyyy}년 ${MM}월 ${dd}일"
    }

    private fun initTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                setFragment(tab?.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                setFragment(tab?.position)
            }
        })
    }

    fun updateSummary(income: Int, consumption: Int, sum: Int) {
        binding.tvIncome.text = income.toString()
        binding.tvConsumption.text = consumption.toString()

        if (sum > 0) {
            binding.tvSum.text = "+$sum"
            binding.tvSum.setTextColor(COLOR_INCOME)
        } else if (sum == 0) {
            binding.tvSum.text = sum.toString()
            binding.tvSum.setTextColor(COLOR_TRANSFER)

        } else {
            binding.tvSum.text = sum.toString()
            binding.tvSum.setTextColor(COLOR_CONSUMPTION)
        }
    }

    private fun initFrameLayout() {
        setFragment(0)

        // handleHorizontalSwipe
        onSwipeTouchListener = object :
            OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeLeftToRight() {
                super.onSwipeLeftToRight()
                setDate(-1)

            }

            override fun onSwipeRightToLeft() {
                super.onSwipeRightToLeft()
                setDate(+1)
            }
        }

        binding.frameLayout.setOnTouchListener(onSwipeTouchListener)
    }

    private fun setClickEvent() {
        binding.btnAddHistory.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.btnLeft.setOnClickListener {
            setDate(-1)
        }

        binding.btnRight.setOnClickListener {
            setDate(+1)
        }
    }

    private fun setFragment(position: Int?) {
        position?.let {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = when (it) {
                0 -> DayFragment(currentDate)
                1 -> CalendarFragment(currentDate)
                2 -> WeekFragment(currentDate)
                3 -> MonthFragment(currentDate)
                4 -> SummaryFragment(currentDate)
                else -> throw NotImplementedError()
            }
            transaction.replace(binding.frameLayout.id, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun setDate(num: Int) {
        // binding.tabLayout.selectedTabPosition
        // 0 : DayFragment, prevDay, nextDay
        // 1 : CalendarFragment, prevMonth, nextMonth
        // 2 : WeekFragment, prevMonth, nextMonth
        // 3 : MonthFragment, prevYear, nextYear
        // 4 : SummaryFragment, prevYear, nextYear


        currentDate?.let {
            currentDate = DateUtil.getTargetDate(it, binding.tabLayout.selectedTabPosition, num)
//            binding.tvDate.text = "${targetYyyy}년 ${targetMm}월 ${targetDd}일"
            binding.tvDate.text = "${currentDate?.substring(0, 4)}년 ${currentDate?.substring(4, 6)}월 ${currentDate?.substring(6, 8)}일"

            val currentPosition = binding.tabLayout.selectedTabPosition
            binding.tabLayout.getTabAt(currentPosition)?.select()
        }
    }

    // this made it work
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        onSwipeTouchListener.gestureDetector?.onTouchEvent(event)
        // Be sure to call the superclass implementation
        return super.dispatchTouchEvent(event)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}