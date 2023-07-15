package com.example.accountbookuisampling.registerinput.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.room.PrimaryKey
import com.example.accountbookuisampling.databinding.ActivityAddAssetTextInputBinding
import com.example.accountbookuisampling.registerinput.fragment.RegisterInputAmountFragment
import com.example.accountbookuisampling.room.entity.Asset
import com.example.accountbookuisampling.util.*
import com.google.android.material.textfield.TextInputEditText

class AddAssetInputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAssetTextInputBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAssetTextInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        disableKeyboard()
        setFocusChangeEvent()
        setClickEvent()
    }

    private fun setFocusChangeEvent() {
        binding.etName.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hideCurrentInputView()
            }
        }

        binding.etAmount.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hideKeyboard()
                showInputFragment(FLAG_AMOUNT)
            }
        }

        binding.etMemo.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hideCurrentInputView()
            }
        }
    }

    fun changeInputAmount(value: String) {
        binding.etAmount.setText(value)
        binding.etAmount.clearFocus()
        hideCurrentInputView()
    }

    private fun setClickEvent() {

        binding.btnSave.setOnClickListener {
            save()
        }

        binding.layoutWrap.setOnClickListener {
            hideCurrentInputView()
            if (it !is TextInputEditText) {
                hideKeyboard()
            }
        }
    }

    private fun save() {

        val name = binding.etName.text.toString()
        val amount = binding.etAmount.text.toString()
        val memo = binding.etMemo.text.toString()

        if (!isValidate(name, amount)) return
        val intent = Intent()
        intent.putExtra(TEXT_NAME, name)
        intent.putExtra(TEXT_AMOUNT, if(amount.isNullOrEmpty() || amount.length == 1) 0 else amount.substring(1).toInt())
        intent.putExtra(TEXT_MEMO, memo)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun isValidate(name: String, amount: String): Boolean {
        if (name.trim().isNullOrEmpty()) return false
        try {
            if (!(amount.trim().isNullOrEmpty()) && amount.length > 1) {
                amount.substring(1).toInt()
            }
        } catch (e: NumberFormatException) {
            return false
        }
        return true
    }

    override fun onBackPressed() {
        if (binding.frameLayout.visibility == View.VISIBLE) {
            hideCurrentInputView()
        } else {
            super.onBackPressed()
        }
    }

    private fun showInputFragment(flag: Int) {
        when (flag) {
            FLAG_AMOUNT -> {
                val transaction = supportFragmentManager.beginTransaction()
                val fragment =
                    RegisterInputAmountFragment(binding.etAmount.text.toString(), FLAG_AMOUNT)
                transaction.replace(binding.frameLayout.id, fragment)
                transaction.addToBackStack(null)
                transaction.commit()

                if (binding.frameLayout.visibility == View.GONE) binding.frameLayout.visibility =
                    View.VISIBLE
            }
        }
    }


    private fun disableKeyboard() {
        binding.etAmount.showSoftInputOnFocus = false
    }

    private fun hideCurrentInputView() {
        binding.frameLayout.visibility = View.GONE
        binding.frameLayout.removeAllViews()
    }

    private fun hideKeyboard() {
        val im =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        currentFocus?.clearFocus()
    }
}