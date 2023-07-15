package com.example.accountbookuisampling.registerinput.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.registerinput.activity.SelectRepeatActivity
import com.example.accountbookuisampling.databinding.*

class SelectRepeatAdapter(
    repeatList: Array<String>,
    private val parentActivity: SelectRepeatActivity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = repeatList
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemSelectRepeatBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SelectRepeatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as SelectRepeatViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class SelectRepeatViewHolder(private val binding: RvItemSelectRepeatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.btnInput.text = item
            binding.btnInput.setOnClickListener {
                val intent = Intent()
                intent.putExtra("item", item)
                parentActivity.setResult(Activity.RESULT_OK, intent)
                parentActivity.finish()
            }
       }
    }
}