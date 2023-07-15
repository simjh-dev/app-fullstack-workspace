package com.example.accountbookuisampling.registerinput.activity

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.registerinput.adapter.SelectInstallmentNumberPlateAdapter
import com.example.accountbookuisampling.databinding.ActivitySelectInstallmentBinding
import com.example.accountbookuisampling.util.*

class SelectInstallmentActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySelectInstallmentBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectInstallmentBinding.inflate(layoutInflater)
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
        binding.btnComplete.setOnClickListener {
            val result = binding.tvResult.text.toString()
            if(result != TEXT_EMPTY) {
                val intent = Intent()
                intent.putExtra("item", result)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun setRecyclerView() {
        // set recycler view
        binding.recyclerView.layoutManager = getLayoutManager(INPUT_ITEM_VIEW_SPAN_COUNT)
        binding.recyclerView.adapter = SelectInstallmentNumberPlateAdapter(NUMBER_PLATE_LIST, binding)
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