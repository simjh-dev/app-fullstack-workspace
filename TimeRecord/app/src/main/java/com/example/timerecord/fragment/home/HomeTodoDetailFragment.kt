package com.example.timerecord.fragment.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timerecord.R
import com.example.timerecord.adapter.TodoHistoryAdapter
import com.example.timerecord.databinding.FragmentHomeTodoDetailBinding
import com.example.timerecord.util.Const
import com.example.timerecord.util.Const.FLAG_END_TIME
import com.example.timerecord.util.Const.FLAG_START_TIME
import com.example.timerecord.util.Util
import com.example.timerecord.viewmodel.TodoHistoryViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar

class HomeTodoDetailFragment : Fragment() {

    private var _binding: FragmentHomeTodoDetailBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())
    private var _list: ArrayList<TodoHistoryViewModel>? = null
    val list get() = _list!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeTodoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//            arguments?.getString("item")?.let {
//                val item = Gson().fromJson(it, Todo::class.java)
//                val list = RoomDBHelper.getTodoHistory(item)
//                Log.e(TAG, "item: $item")
//            }
        _list = arrayListOf()
        list.addAll(Const.TEMP_TODO_HISTORY_HEAD_LIST)
        list.addAll(
            Util.fillTodoHistoryViewModelList(
                Util.getToday(),
                Const.TEMP_TODO_HISTORY_CONTENT_LIST
            )
        )
        setCalendar(list)

        setClickEvent()
        setBackBtnEvent()
        setCurrentTime()
        setBindingData(list.filter { item -> item.targetDate == Util.getToday() }[0])
    }

    private fun setClickEvent() {
        binding.btnLeft.setOnClickListener {
            binding.showUI = false
            _list = arrayListOf()
            list.addAll(Const.TEMP_TODO_HISTORY_HEAD_LIST)
            val c = Calendar.getInstance()
            c.time = SimpleDateFormat("yyyyMMdd").parse(binding.selectedDate.toString())
            c.add(Calendar.MONTH, -1)
            c.set(Calendar.DAY_OF_MONTH, 1)
            val new = SimpleDateFormat("yyyyMMdd").format(c.time)
            list.addAll(
                Util.fillTodoHistoryViewModelList(
                    new,
                    Const.TEMP_TODO_HISTORY_CONTENT_LIST
                )
            )
            binding.selectedDate = new
            setCalendar(list)
            setBindingData(list.filter { item -> item.targetDate == binding.selectedDate }[0])
        }

        binding.btnRight.setOnClickListener {
            binding.showUI = false
            _list = arrayListOf()
            list.addAll(Const.TEMP_TODO_HISTORY_HEAD_LIST)
            val c = Calendar.getInstance()
            c.time = SimpleDateFormat("yyyyMMdd").parse(binding.selectedDate.toString())
            c.add(Calendar.MONTH, 1)
            c.set(Calendar.DAY_OF_MONTH, 1)
            val new = SimpleDateFormat("yyyyMMdd").format(c.time)
            list.addAll(
                Util.fillTodoHistoryViewModelList(
                    new,
                    Const.TEMP_TODO_HISTORY_CONTENT_LIST
                )
            )
            binding.selectedDate = new
            setCalendar(list)
            setBindingData(list.filter { item -> item.targetDate == binding.selectedDate }[0])
        }

        binding.btnStartTime.setOnClickListener {
            list.forEachIndexed { index, item ->
                if (item.targetDate == binding.selectedDate) {
                    item.startTime =
                        SimpleDateFormat("HHmm").format(SimpleDateFormat("HH:mm:ss").parse(binding.currentTime).time)
                    list[index] = item
                    setBindingData(item)
                    binding.recyclerView.adapter?.notifyItemChanged(index)
                }
            }
        }

        binding.btnEndTime.setOnClickListener {
            list.forEachIndexed { index, item ->
                if (item.targetDate == binding.selectedDate) {
                    item.endTime =
                        SimpleDateFormat("HHmm").format(SimpleDateFormat("HH:mm:ss").parse(binding.currentTime).time)
                    list[index] = item
                    setBindingData(item)
                    binding.recyclerView.adapter?.notifyItemChanged(index)
                }
            }
        }

        binding.cardViewStartTime.setOnClickListener {
            showEditDialog(FLAG_START_TIME)
        }

        binding.cardViewEndTime.setOnClickListener {
            showEditDialog(FLAG_END_TIME)
        }
    }

    private fun showEditDialog(flag: Int) {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_edit_hour)

        val etHour = dialog.findViewById<TextInputEditText>(R.id.et_hour)
        when (flag) {
            FLAG_START_TIME -> {
                val value = binding.tvStartTime.text?.toString()
                if (value == null || value.trim().isEmpty() || value == "Not yet") {

                } else {
                    etHour.setText(value)
                }
            }
            FLAG_END_TIME -> {
                val value = binding.tvEndTime.text?.toString()
                if (value == null || value.trim().isEmpty() || value == "Not yet") {

                } else {
                    etHour.setText(value)
                }
            }
        }

        val btnCancel = dialog.findViewById<MaterialCardView>(R.id.card_view_cancel)
        val btnOk = dialog.findViewById<MaterialCardView>(R.id.card_view_ok)

        etHour.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (Util.isValidateToTime(etHour.text?.toString())) {

                } else {
                    etHour.error = "please check input data in hour"
                }
            }
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnOk.setOnClickListener {
            if (Util.isValidateToTime(etHour.text?.toString())) {
                when (flag) {
                    FLAG_START_TIME -> {
                        binding.tvStartTime.text = etHour.text
                    }
                    FLAG_END_TIME -> {
                        binding.tvEndTime.text = etHour.text
                    }
                }
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setBindingData(item: TodoHistoryViewModel) {
        binding.selectedDate = item.targetDate
        (binding.recyclerView.adapter as TodoHistoryAdapter?)?.selectedDate = item.targetDate
        binding.selectedStartTime = item.startTime
        binding.selectedEndTime = item.endTime
    }

    private fun setCurrentTime() {
        binding.currentTime = Util.getCurrentTime()
        handler.postDelayed({
            setCurrentTime()
        }, Const.DELAY_ONE_SECONDS)
    }

    private fun setCalendar(input: List<TodoHistoryViewModel>) {
        handler.postDelayed({
            val layoutManager = GridLayoutManager(requireContext(), 7)
            layoutManager.orientation =
                LinearLayoutManager.VERTICAL
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.post {
                binding.recyclerView.adapter =
                    TodoHistoryAdapter(input, binding.selectedDate, ::changeDate)
                binding.showUI = true
            }
        }, Const.DELAY_SHOW_UI)
    }

    private fun changeDate(value: String, newIdx: Int) {
        val old = binding.selectedDate.toString()
        // old item refresh
        (binding.recyclerView.adapter as TodoHistoryAdapter).list.forEachIndexed { index, item ->
            if (item.targetDate == old) binding.recyclerView.adapter?.notifyItemChanged(index)
        }
        setBindingData(list[newIdx])
        // new item refresh
        binding.recyclerView.adapter?.notifyItemChanged(newIdx)
    }

    private fun setBackBtnEvent() {
        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    Navigation.findNavController(requireView()).navigateUp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("app:computeSelectedTime")
        fun computeSelectedTime(view: View, selectedDate: String) {

        }
    }
}