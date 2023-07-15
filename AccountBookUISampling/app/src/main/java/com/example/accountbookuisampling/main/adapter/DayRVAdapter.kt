package com.example.accountbookuisampling.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.databinding.RvItemDayBinding
import com.example.accountbookuisampling.util.COLOR_CONSUMPTION
import com.example.accountbookuisampling.util.COLOR_INCOME
import com.example.accountbookuisampling.main.viewmodel.DayViewModel
import com.example.accountbookuisampling.util.COLOR_TRANSFER
import kotlin.collections.ArrayList

class DayRVAdapter(private val list: ArrayList<DayViewModel>) :
    RecyclerView.Adapter<DayRVAdapter.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RvItemDayBinding =
            RvItemDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = _list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class ViewHolder(val binding: RvItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DayViewModel) {
            // income
            if(item.type == 0) {
                binding.tvAmount.setTextColor(COLOR_INCOME)
            }
            // consumption
            else if(item.type == 1) {
                binding.tvAmount.setTextColor(COLOR_CONSUMPTION)
            }
            else {
                binding.tvAmount.setTextColor(COLOR_TRANSFER)
            }
            binding.model = item
        }

    }
}

