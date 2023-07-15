package com.example.accountbookuisampling.register.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.accountbookuisampling.R
import com.example.accountbookuisampling.application.BaseApplication
import com.example.accountbookuisampling.registerinput.activity.SelectRepeatActivity
import com.example.accountbookuisampling.databinding.FragmentIncomeBinding
import com.example.accountbookuisampling.registerinput.fragment.RegisterInputAmountFragment
import com.example.accountbookuisampling.registerinput.fragment.RegisterInputAssetFragment
import com.example.accountbookuisampling.registerinput.fragment.RegisterInputDateFragment
import com.example.accountbookuisampling.registerinput.fragment.RegisterInputCategoryFragment
import com.example.accountbookuisampling.room.entity.History
import com.example.accountbookuisampling.util.*
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class IncomeFragment : BaseRegisterFragment() {

    private lateinit var binding: FragmentIncomeBinding
    private val TAG = this::class.java.simpleName
    private val selectRepeatActivityResultLauncher = getSelectRepeatActivityResultLauncher()
    override var currentView: Int = FLAG_INCOME

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disableKeyboard()
        binding.isImportant = false
        binding.isRepeat = false
        binding.etDate.setText(DateUtil.getTodayText())
    }

    override fun onResume() {
        super.onResume()
        setFocusChangeEvent()
        setClickEvent()
        setBackPressEvent()
    }

    private fun setFocusChangeEvent() {
        binding.etDate.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showInputFragment(FLAG_DATE)
            }
        }
        binding.etAsset.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showInputFragment(FLAG_ASSET)
            }
        }
        binding.etCategory.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showInputFragment(FLAG_CATEGORY)
            }
        }
        binding.etAmount.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showInputFragment(FLAG_AMOUNT)
            }
        }
        binding.etDetail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (binding.frameLayout.visibility == View.VISIBLE) binding.frameLayout.visibility =
                    View.GONE
            }
        }
    }

    private fun setClickEvent() {
        binding.btnDetailOnoff.setOnClickListener {
            binding.isImportant = !binding.isImportant
        }

        binding.btnRepeat.setOnClickListener {
            if (binding.isRepeat) {
                showRepeatAndCancelPopup()
            } else {
                openSelectRepeatActivityResultLauncher()
            }
        }

        binding.layoutEditTextWrap.setOnClickListener {
            hideCurrentInputView()
            if (it !is TextInputEditText) {
                hideKeyboard()
            }
        }
        binding.btnSave.setOnClickListener {
            saveHistory()
        }
    }

    private fun saveHistory() {
        var date = binding.etDate.text.toString()
            .substring(0, 8)    // remove (M)
        val asset = binding.etAsset.text.toString()
        val category = binding.etCategory.text.toString()
        val amount = binding.etAmount.text.toString()
        val detail = binding.etDetail.text.toString()
        val important = binding.isImportant
        val additionDetail = binding.etAdditionDetail.text.toString()

        if (!isValidate(date, asset, category, amount)) return
        date += if (DateUtil.isToday(date)) {
            val cal = Calendar.getInstance()
            cal.get(Calendar.HOUR_OF_DAY)
            // pattern HH : 0 ~ 23
            // pattern hh : 0 ~ 12
            val sdf = SimpleDateFormat("HHmm")
            sdf.format(cal.time)
        } else {
            "0000"
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val assetId =
                (requireActivity().application as BaseApplication).assetDao.getIdByName(asset)
            val categoryId =
                (requireActivity().application as BaseApplication).categoryDao.getIdByName(category)
            val item = History(
                0,
                currentView,
                date,
                assetId,
                asset,
                categoryId,
                category,
                amount
                    .substring(1).toInt(), // remove money symbol
                0,
                detail,
                important,
                additionDetail,
                null
            )

            (requireActivity().application as BaseApplication).historyDao.insert(item)
            lifecycleScope.launch(Dispatchers.Main) {
                requireActivity().finish()
            }
        }
    }

    private fun isValidate(date: String, asset: String, category: String, amount: String): Boolean {
        // date validate
        val sdf = SimpleDateFormat("yyyyMMdd")
        sdf.isLenient = false
        try {
            sdf.parse(date)
        } catch (e: ParseException) {
            return false
        }

        // asset validate
        if (asset.trim().isNullOrEmpty()) return false

        // category validate
        if (category.trim().isNullOrEmpty()) return false

        // amount validate
        if (amount.trim().isNullOrEmpty()) return false
        try {
            amount.substring(1).toInt()
        } catch (e: NumberFormatException) {
            return false
        }

        return true
    }

    private fun setBackPressEvent() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (binding.frameLayout.visibility == View.VISIBLE) {
                hideCurrentInputView()
            } else {
                requireActivity().finish()
            }
        }
    }

    private fun showInputFragment(flag: Int) {
        hideKeyboard()

        val transaction = childFragmentManager.beginTransaction()
        val fragment = when (flag) {
            FLAG_DATE -> {
                // yyyyMMdd
                RegisterInputDateFragment(binding.etDate.text.toString().substring(0, 8))
            }
            FLAG_ASSET -> {
                RegisterInputAssetFragment()
            }
            FLAG_CATEGORY -> {
                RegisterInputCategoryFragment()
            }
            FLAG_AMOUNT -> {
                RegisterInputAmountFragment(binding.etAmount.text.toString(), FLAG_AMOUNT)
            }
            else -> throw NotImplementedError()
        }

        transaction.replace(binding.frameLayout.id, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

        // もし、キーボートが開いているとしたら
        if (binding.frameLayout.visibility == View.GONE) binding.frameLayout.visibility =
            View.VISIBLE
    }

    override fun changeDateFromChild(value: String) {
        binding.etDate.setText(value)
    }

    override fun changeInputTextFromChild(value: String, flag: Int) {
        when (flag) {
            FLAG_ASSET -> {
                binding.etAsset.setText(value)
                binding.etAsset.clearFocus()
                if (binding.etCategory.text.toString().isEmpty()) binding.etCategory.requestFocus()
                else if (binding.etAmount.text.toString().isEmpty()) binding.etAmount.requestFocus()
            }
            FLAG_CATEGORY -> {
                binding.etCategory.setText(value)
                binding.etCategory.clearFocus()
                if (binding.etAmount.text.toString().isEmpty()) binding.etAmount.requestFocus()
            }
        }
    }

    override fun changeInputAmountFromChild(value: String, flag: Int) {
        binding.etAmount.setText(value)
        binding.etAmount.clearFocus()
        if (binding.etDetail.text.toString().isEmpty()) {
            binding.etDetail.requestFocus()

            val im =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.showSoftInput(binding.etDetail, 0)
        }
    }

    override fun closeInputLayout() {
        hideCurrentInputView()
        requireActivity().currentFocus?.clearFocus()
    }

    private fun disableKeyboard() {
        binding.etDate.showSoftInputOnFocus = false
        binding.etAsset.showSoftInputOnFocus = false
        binding.etCategory.showSoftInputOnFocus = false
        binding.etAmount.showSoftInputOnFocus = false
    }

    private fun hideCurrentInputView() {
        binding.frameLayout.visibility = View.GONE
        binding.frameLayout.removeAllViews()
    }

    private fun hideKeyboard() {
        val im =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        requireActivity().currentFocus?.clearFocus()
    }

    private fun openSelectRepeatActivityResultLauncher() {
        val intent = Intent(
            requireContext(),
            SelectRepeatActivity::class.java
        )
        selectRepeatActivityResultLauncher.launch(
            intent
        )
    }

    private fun getSelectRepeatActivityResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { data ->
                    when (val value = data.getStringExtra("item")) {
                        TEXT_EMPTY -> return@registerForActivityResult
                        TEXT_NONE -> return@registerForActivityResult
                        else -> {
                            binding.isRepeat = true
                            binding.textRepeat = value
                        }
                    }
                }
            }
        }
    }

    private fun showRepeatAndCancelPopup() {
        val popup = PopupMenu(requireContext(), binding.btnRepeat)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.select_repeat -> {
                    openSelectRepeatActivityResultLauncher()
                    true
                }
                R.id.select_cancel -> {
                    binding.isRepeat = false
                    true
                }
            }
            false
        }
        popup.menuInflater.inflate(R.menu.menu_select_repeat_and_cancel, popup.menu)
        popup.show()
    }

    companion object {
        private var instance: IncomeFragment? = null

        @JvmStatic
        fun getInstance(): IncomeFragment {
            if (instance == null) {
                instance = IncomeFragment()
                return instance as IncomeFragment
            }

            return instance as IncomeFragment
        }
    }
}