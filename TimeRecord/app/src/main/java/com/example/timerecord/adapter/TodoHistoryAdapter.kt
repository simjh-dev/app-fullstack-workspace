package com.example.timerecord.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.timerecord.databinding.RvItemTodoHistoryContentBinding
import com.example.timerecord.databinding.RvItemTodoHistoryHeadBinding
import com.example.timerecord.util.Const.TYPE_CALENDAR_CONTENT_INVISIBLE
import com.example.timerecord.util.Const.TYPE_CALENDAR_CONTENT_VISIBLE
import com.example.timerecord.util.Const.TYPE_CALENDAR_HEAD
import com.example.timerecord.viewmodel.TodoHistoryViewModel

class TodoHistoryAdapter(
    private val _list: List<TodoHistoryViewModel>,
    private val _selectedDate: String?,
    private val changeDate: (String, Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val list = _list
    private val TAG = this::class.java.simpleName
    var selectedDate = _selectedDate

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CALENDAR_HEAD -> {
                val binding = RvItemTodoHistoryHeadBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
//                val layoutParams = binding.root.layoutParams
//                layoutParams.height = headHeight
//                binding.root.layoutParams = layoutParams
                TodoHistoryHeadViewHolder(binding)
            }
            TYPE_CALENDAR_CONTENT_VISIBLE -> {
                val binding = RvItemTodoHistoryContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TodoHistoryContentViewHolder(binding)
            }
            TYPE_CALENDAR_CONTENT_INVISIBLE -> {
                val binding = RvItemTodoHistoryContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TodoHistoryContentViewHolder(binding)
            }
            else -> {
                throw NotImplementedError()
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        when (item.viewType) {
            TYPE_CALENDAR_HEAD -> {
                (holder as TodoHistoryHeadViewHolder).bind(item)
            }
            TYPE_CALENDAR_CONTENT_VISIBLE -> {
                (holder as TodoHistoryContentViewHolder).bind(item, position)
            }
            TYPE_CALENDAR_CONTENT_INVISIBLE -> {
                (holder as TodoHistoryContentViewHolder).bind(item, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }

    inner class TodoHistoryHeadViewHolder(private val binding: RvItemTodoHistoryHeadBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TodoHistoryViewModel) {
            binding.strDayOfWeek = item.strDayOfWeek
        }
    }

    inner class TodoHistoryContentViewHolder(private val binding: RvItemTodoHistoryContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TodoHistoryViewModel, position: Int) {
            if (item.viewType == TYPE_CALENDAR_CONTENT_VISIBLE) {
                binding.isVisible = true

                binding.viewModel = item
                binding.tvTargetDate.text = "${item.targetDate.substring(6, 8).toInt()}"
                binding.isSelected = item.targetDate == selectedDate

                binding.root.setOnClickListener {
                    changeDate(item.targetDate, position)
                }
            }
            // WHEN THE TYPE IS INVISIBLE
            else {
                binding.isVisible = false
                // do not anything..

            }
        }
    }

}