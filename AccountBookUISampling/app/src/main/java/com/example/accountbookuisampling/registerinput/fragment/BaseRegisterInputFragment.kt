package com.example.accountbookuisampling.registerinput.fragment

import androidx.fragment.app.Fragment
import com.example.accountbookuisampling.util.TEXT_EMPTY

abstract class BaseRegisterInputFragment: Fragment() {

    open fun changeDate(value: String) {}
    open fun confirmDate(value: String) {}
    open fun changeInputText(value: String) {}
    open fun changeInputAmount(value: String) {}
    open fun confirmInputAmount(value: String) {}
    open fun getInputAmountValue() : String {
        return TEXT_EMPTY
    }
    open fun openAddTextInputActivityResultLauncher() {}

}