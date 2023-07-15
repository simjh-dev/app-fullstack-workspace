package com.example.accountbookuisampling.registerinput.fragment

import android.animation.ObjectAnimator
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.databinding.FragmentRegisterInputDateBinding
import com.example.accountbookuisampling.register.fragment.BaseRegisterFragment
import com.example.accountbookuisampling.registerinput.adapter.InputDateAdapter
import com.example.accountbookuisampling.registerinput.dataclass.InputDateItem
import com.example.accountbookuisampling.util.*

class RegisterInputDateFragment(private val date: String) : BaseRegisterInputFragment() {

    private lateinit var binding: FragmentRegisterInputDateBinding
    private val TAG = this::class.java.simpleName
    private var _date = date
//    private var onSwipeTouchListener: OnSwipeTouchListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterInputDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setClickEvent()
        setView()
    }

    private fun setClickEvent() {
        binding.btnClose.setOnClickListener {
            (requireParentFragment() as BaseRegisterFragment).closeInputLayout()
        }

        binding.btnLeft.setOnClickListener {
            changeDate(DateUtil.getPrevMonthDate(_date))
        }

        binding.btnRight.setOnClickListener {
            changeDate(DateUtil.getNextMonthDate(_date))
        }

        binding.btnToday.setOnClickListener {
            // yyyyMMdd
            changeDate(DateUtil.getTodayText().substring(0, 8))
        }
    }

    private fun setView() {
        binding.tvTitle.text = TEXT_DATE
        binding.tvMonth.text = "${_date.substring(4, 6)}$TEXT_MONTH"
        setLayoutManager()
        val dateList = DateUtil.getDateList(_date)
        val contentList = ArrayList<InputDateItem>()
        dateList.forEachIndexed { index, date ->
            contentList.add(
                InputDateItem(TYPE_CALENDAR_CONTENT, index % 7, date)
            )
        }
        val list = ArrayList<InputDateItem>()
        list.addAll(INPUT_DATE_HEAD_LIST)
        list.addAll(contentList)

        binding.recyclerView.adapter = InputDateAdapter(_date, list, this)
        binding.recyclerView.setOnTouchListener(object :
            OnSwipeTouchListener(requireParentFragment().requireActivity()) {
            override fun onSwipeLeftToRight() {
                super.onSwipeLeftToRight()
                changeDate(DateUtil.getPrevMonthDate(_date))
            }

            override fun onSwipeRightToLeft() {
                super.onSwipeRightToLeft()
                changeDate(DateUtil.getNextMonthDate(_date))
            }
        })
    }

    override fun changeDate(value: String) {
        _date = value
        (requireParentFragment() as BaseRegisterFragment).changeDateFromChild(
            DateUtil.getDateText(
                value
            )
        )
        ObjectAnimator.ofFloat(binding.recyclerView, TEXT_ALPHA, 1f, 0.5f, 0f, 0.5f, 1f).apply {
            duration = DURATION_ALPHA
            start()
        }
        setView()
    }

    override fun confirmDate(value: String) {
        (requireParentFragment() as BaseRegisterFragment).changeDateFromChild(
            DateUtil.getDateText(
                value
            )
        )
        (requireParentFragment() as BaseRegisterFragment).closeInputLayout()
    }



    private fun setLayoutManager() {
        // set recyclerview layout like grid view
        val layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(requireContext(), CALENDAR_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(requireContext(), CALENDAR_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                layoutManager
            }
            else -> throw NotImplementedError()
        }
        binding.recyclerView.layoutManager = layoutManager
    }
}