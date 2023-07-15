package com.example.accountbookuisampling.registerinput.fragment

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.PrimaryKey
import com.example.accountbookuisampling.application.BaseApplication
import com.example.accountbookuisampling.databinding.FragmentRegisterInputAssetBinding
import com.example.accountbookuisampling.register.fragment.BaseRegisterFragment
import com.example.accountbookuisampling.registerinput.activity.AddAssetInputActivity
import com.example.accountbookuisampling.registerinput.adapter.InputAssetAdapter
import com.example.accountbookuisampling.room.entity.Asset
import com.example.accountbookuisampling.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterInputAssetFragment : BaseRegisterInputFragment() {

    private lateinit var binding: FragmentRegisterInputAssetBinding
    private val TAG = this::class.java.simpleName
    private val addTextInputActivityResultLauncher = getAddTextInputActivityResultLauncher()
    private val _list = ArrayList<Asset>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterInputAssetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setClickEvent()
        setData()
    }

    private fun setClickEvent() {

        binding.btnViewType.setOnClickListener {
            Log.e(TAG, "btnViewType")
        }

        binding.btnEdit.setOnClickListener {
            Log.e(TAG, "btnEdit")
        }

        binding.btnClose.setOnClickListener {
            (requireParentFragment() as BaseRegisterFragment).closeInputLayout()
        }
    }

    private fun setData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val list =
                (requireActivity().application as BaseApplication).assetDao.getByType((requireParentFragment() as BaseRegisterFragment).currentView)
            lifecycleScope.launch(Dispatchers.Main) {
                _list.clear()
                _list.addAll(list)
                setView()
            }
        }
    }

    private fun setView() {
        binding.tvTitle.text = TEXT_ASSET
        setLayoutManager()

        if (_list[_list.size - 1].name != TEXT_ADD) {
            _list.add(
                Asset(
                    -1,
                    (requireParentFragment() as BaseRegisterFragment).currentView,
                    TEXT_ADD,
                    -1,
                    null
                )
            )
        }
        binding.recyclerView.adapter =
            InputAssetAdapter(_list, this)
    }

    private fun setLayoutManager() {
        // set recyclerview layout like grid view
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

    override fun openAddTextInputActivityResultLauncher() {
        val intent = Intent(
            requireContext(),
            AddAssetInputActivity::class.java
        )
        addTextInputActivityResultLauncher.launch(
            intent
        )
    }

    override fun changeInputText(value: String) {
        (requireParentFragment() as BaseRegisterFragment).changeInputTextFromChild(
            value, FLAG_ASSET
        )
    }

    private fun getAddTextInputActivityResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { data ->
                    val name = data.getStringExtra(TEXT_NAME)
                    val amount = data.getIntExtra(TEXT_AMOUNT, 0)
                    val memo = data.getStringExtra(TEXT_MEMO)

                    name?.let {
                        val item = Asset(
                            0,
                            (requireParentFragment() as BaseRegisterFragment).currentView,
                            name,
                            amount,
                            memo
                        )

                        lifecycleScope.launch(Dispatchers.IO) {
                            (requireActivity().application as BaseApplication).assetDao.insert(item)
                            lifecycleScope.launch(Dispatchers.Main) {
                                setData()
                            }
                        }

                    }
                }
            }
        }
    }

}