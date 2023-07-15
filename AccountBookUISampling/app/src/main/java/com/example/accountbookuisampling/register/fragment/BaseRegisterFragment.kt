package com.example.accountbookuisampling.register.fragment

import androidx.fragment.app.Fragment

abstract class BaseRegisterFragment: Fragment() {

    abstract var currentView: Int
    open fun changeDateFromChild(value: String) {}
    open fun changeInputTextFromChild(value: String, flag: Int) {}
    open fun changeInputAmountFromChild(value: String, flag: Int) {}
    open fun closeInputLayout() {}

}