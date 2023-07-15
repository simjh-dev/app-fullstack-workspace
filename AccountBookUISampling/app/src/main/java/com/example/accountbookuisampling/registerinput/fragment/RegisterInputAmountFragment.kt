package com.example.accountbookuisampling.registerinput.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.databinding.FragmentRegisterInputAmountBinding
import com.example.accountbookuisampling.main.activity.MainActivity
import com.example.accountbookuisampling.register.activity.RegisterActivity
import com.example.accountbookuisampling.register.fragment.BaseRegisterFragment
import com.example.accountbookuisampling.registerinput.activity.AddAssetInputActivity
import com.example.accountbookuisampling.registerinput.adapter.InputCalculateAdapter
import com.example.accountbookuisampling.util.CALCULATOR_ITEM_LIST
import com.example.accountbookuisampling.util.FLAG_ASSET
import com.example.accountbookuisampling.util.INPUT_ITEM_VIEW_SPAN_COUNT
import com.example.accountbookuisampling.util.TEXT_AMOUNT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterInputAmountFragment(private val amount: String, private val flag: Int) : BaseRegisterInputFragment() {

    private lateinit var binding: FragmentRegisterInputAmountBinding
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterInputAmountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setClickEvent()
        setData()
        setView()
    }

    private fun setClickEvent() {
        binding.btnClose.setOnClickListener {
            (requireParentFragment() as BaseRegisterFragment).closeInputLayout()
        }
    }

    private fun setData() {
        lifecycleScope.launch(Dispatchers.IO) {
//            val list = (requireActivity().application as BaseApplication).assetDao.getAll()
            lifecycleScope.launch(Dispatchers.Main) {
//                (requireActivity() as MainActivity).updateSummary(1000, 2000, 1000 - 2000)
            }
        }
    }

    private fun setView() {
        binding.tvTitle.text = TEXT_AMOUNT

        binding.etInput.setText(amount)
        binding.etInput.showSoftInputOnFocus = false

        setLayoutManager()
        binding.recyclerView.adapter =
            InputCalculateAdapter(CALCULATOR_ITEM_LIST, this)
    }

    private fun setLayoutManager() {
        val layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(requireContext(), INPUT_ITEM_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(requireContext(), INPUT_ITEM_VIEW_SPAN_COUNT)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                layoutManager
            }
            else -> throw NotImplementedError()
        }
        binding.recyclerView.layoutManager = layoutManager
    }

    override fun changeInputAmount(value: String) {
        binding.etInput.setText(value)
    }

    override fun confirmInputAmount(value: String) {
        if(requireActivity() is RegisterActivity) {
            (requireParentFragment() as BaseRegisterFragment).changeInputAmountFromChild(
                value, flag
            )
        }
        else if(requireActivity() is AddAssetInputActivity) {
            (requireActivity() as AddAssetInputActivity).changeInputAmount(value)
        }
    }

    override fun getInputAmountValue(): String {
        return binding.etInput.text.toString()
    }
}