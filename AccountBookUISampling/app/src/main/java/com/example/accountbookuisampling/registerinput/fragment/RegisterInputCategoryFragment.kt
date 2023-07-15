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
import com.example.accountbookuisampling.databinding.FragmentRegisterInputCategoryBinding
import com.example.accountbookuisampling.register.fragment.BaseRegisterFragment
import com.example.accountbookuisampling.registerinput.activity.AddCategoryInputActivity
import com.example.accountbookuisampling.registerinput.adapter.InputCategoryAdapter
import com.example.accountbookuisampling.room.entity.Asset
import com.example.accountbookuisampling.room.entity.Category
import com.example.accountbookuisampling.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterInputCategoryFragment : BaseRegisterInputFragment() {

    private lateinit var binding: FragmentRegisterInputCategoryBinding
    private val TAG = this::class.java.simpleName
    private val addTextInputActivityResultLauncher = getAddTextInputActivityResultLauncher()
    private val _list = ArrayList<Category>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterInputCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setClickEvent()
        setData()
    }

    private fun setClickEvent() {
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
                (requireActivity().application as BaseApplication).categoryDao.getByType((requireParentFragment() as BaseRegisterFragment).currentView)
            lifecycleScope.launch(Dispatchers.Main) {
                _list.clear()
                _list.addAll(list)
                setView()
            }
        }
    }

    private fun setView() {
        binding.tvTitle.text = TEXT_CATEGORY

        if (_list[_list.size - 1].name != TEXT_ADD) {
            _list.add(
                Category(
                    -1,
                    (requireParentFragment() as BaseRegisterFragment).currentView,
                    TEXT_ADD,
                    null
                )
            )
        }
        setLayoutManager()
        binding.recyclerView.adapter =
            InputCategoryAdapter(_list, this)
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
            AddCategoryInputActivity::class.java
        )
        addTextInputActivityResultLauncher.launch(
            intent
        )
    }

    override fun changeInputText(value: String) {
        (requireParentFragment() as BaseRegisterFragment).changeInputTextFromChild(
            value, FLAG_CATEGORY
        )
    }

    private fun getAddTextInputActivityResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { data ->
                    val name = data.getStringExtra(TEXT_NAME)
                    val memo = data.getStringExtra(TEXT_MEMO)

                    name?.let {
                        val item = Category(
                            0,
                            (requireParentFragment() as BaseRegisterFragment).currentView,
                            name,
                            memo
                        )

                        lifecycleScope.launch(Dispatchers.IO) {
                            (requireActivity().application as BaseApplication).categoryDao.insert(
                                item
                            )
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