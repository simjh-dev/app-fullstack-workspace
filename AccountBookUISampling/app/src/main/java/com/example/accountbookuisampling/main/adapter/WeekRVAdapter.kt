package com.example.accountbookuisampling.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.databinding.RvItemWeekBinding
import com.example.accountbookuisampling.main.viewmodel.WeekViewModel

class WeekRVAdapter(private val list: ArrayList<WeekViewModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WeekViewHolder(
            RvItemWeekBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val week = _list[position]
        (holder as WeekViewHolder).bind(week)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class WeekViewHolder(private val binding: RvItemWeekBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeekViewModel) {
            val model = formatToViewModel(item)
            binding.model = model
        }

        private fun formatToViewModel(item: WeekViewModel): WeekViewModel {
            return item
        }
    }

}