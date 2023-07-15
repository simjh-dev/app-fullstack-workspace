package com.example.accountbookuisampling.registerinput.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.example.accountbookuisampling.databinding.ActivityAddAssetTextInputBinding
import com.example.accountbookuisampling.databinding.ActivityAddCategoryTextInputBinding
import com.example.accountbookuisampling.util.*
import com.google.android.material.textfield.TextInputEditText

class AddCategoryInputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCategoryTextInputBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCategoryTextInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnSave.setOnClickListener {
            save()
        }

        binding.layoutInput.setOnClickListener {
            if (it !is TextInputEditText) {
                hideKeyboard()
            }
        }
    }

    private fun save() {
        val name = binding.etName.text.toString()
        val memo = binding.etMemo.text.toString()
        if (!isValidate(name)) return

        val intent = Intent()
        intent.putExtra(TEXT_NAME, name)
        intent.putExtra(TEXT_MEMO, memo)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun isValidate(name: String): Boolean {
        if (name.trim().isNullOrEmpty()) return false

        return true
    }

    private fun hideKeyboard() {
        val im =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        currentFocus?.clearFocus()
    }
}