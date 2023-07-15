package com.example.timerecord.adapter

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.timerecord.R
import com.example.timerecord.databinding.RvItemTodoBinding
import com.example.timerecord.entity.Todo
import com.google.gson.Gson

class TodoAdapter(private val _list: List<Todo>, private val activity: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = _list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            RvItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TodoViewHolder).bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class TodoViewHolder(private val binding: RvItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Todo) {
            binding.viewModel = item

            binding.root.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("item", Gson().toJson(item))
                Navigation.findNavController(activity, R.id.fragment_container)
                    .navigate(R.id.action_default_to_todo_detail, bundle)
            }
        }
    }

}