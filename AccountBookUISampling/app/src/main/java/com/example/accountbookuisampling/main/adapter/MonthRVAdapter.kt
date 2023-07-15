package com.example.accountbookuisampling.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.databinding.RvItemMonthBinding
import com.example.accountbookuisampling.main.viewmodel.MonthViewModel
import kotlin.collections.ArrayList

class MonthRVAdapter(private val list: ArrayList<MonthViewModel>) :
    RecyclerView.Adapter<MonthRVAdapter.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RvItemMonthBinding =
            RvItemMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = _list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class ViewHolder(val binding: RvItemMonthBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MonthViewModel) {
            binding.model = item
        }
    }
}

