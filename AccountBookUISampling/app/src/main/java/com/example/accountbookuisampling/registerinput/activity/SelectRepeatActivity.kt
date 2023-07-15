package com.example.accountbookuisampling.registerinput.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.registerinput.adapter.SelectRepeatAdapter
import com.example.accountbookuisampling.databinding.ActivitySelectRepeatBinding
import com.example.accountbookuisampling.util.REPEAT_ITEM_LIST

class SelectRepeatActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySelectRepeatBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectRepeatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setClickEvent()
        setRecyclerView()
    }

    private fun setClickEvent() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.tvBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecyclerView() {
        // set recycler view
        binding.recyclerView.layoutManager = getLayoutManager(1)
        binding.recyclerView.adapter = SelectRepeatAdapter(REPEAT_ITEM_LIST, this)
    }

    private fun getLayoutManager(span: Int): GridLayoutManager {
        // set recyclerview layout like grid view
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(this, span)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(this, span)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                layoutManager
            }
            else -> throw NotImplementedError()
        }
    }
}