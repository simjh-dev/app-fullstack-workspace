package com.example.accountbookuisampling.registerinput.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.databinding.RvItemTextInputBinding
import com.example.accountbookuisampling.registerinput.fragment.BaseRegisterInputFragment
import com.example.accountbookuisampling.room.entity.Category
import com.example.accountbookuisampling.util.TEXT_ADD

class InputCategoryAdapter(private val list: ArrayList<Category>, private val fragment: BaseRegisterInputFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemTextInputBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TextInputViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as TextInputViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class TextInputViewHolder(private val binding: RvItemTextInputBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Category) {
            binding.btnInput.text = item.name
            binding.btnInput.setOnClickListener {
                if (item.name == TEXT_ADD) {
                    fragment.openAddTextInputActivityResultLauncher()
                } else {
                    fragment.changeInputText(item.name)
                }
            }
        }
    }

}