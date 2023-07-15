package com.example.timerecord.fragment.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.timerecord.databinding.FragmentHomeAddTodoBinding
import com.example.timerecord.util.Const
import com.example.timerecord.util.Util
import com.google.android.material.textfield.TextInputEditText

class HomeAddTodoFragment : Fragment() {

    private var _binding: FragmentHomeAddTodoBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeAddTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler.postDelayed({
            setEditTextEvent()
            setRepeatClickEvent()
            setBackBtnEvent()
            binding.showUI = true
        }, Const.DELAY_SHOW_UI)
    }

    private fun setEditTextEvent() {
        binding.layoutWrap.setOnClickListener {
            if (it !is TextInputEditText) {
                hideKeyboard()
            }
        }

        binding.etTitle.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val value = binding.etTitle.text?.toString()
                if (value == null || value.trim().isEmpty()) {
                    binding.etTitle.error = "please check input data in title"
                }
            }
        }

        binding.etStartTime.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (Util.isValidateToTime(binding.etStartTime.text?.toString())) {

                } else {
                    binding.etStartTime.error = "please check input data in start time"
//                    Toast.makeText(requireContext(), "please check input data in start time", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.etEndTime.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (Util.isValidateToTime(binding.etEndTime.text?.toString())) {

                } else {
                    binding.etEndTime.error = "please check input data in end time"
                }
            }
        }
    }

    private fun setRepeatClickEvent() {
        binding.btnSunday.setOnClickListener {
            binding.isSelectedSunday = !(binding.isSelectedSunday ?: false)
        }
        binding.btnMonday.setOnClickListener {
            binding.isSelectedMonday = !(binding.isSelectedMonday ?: false)
        }
        binding.btnTuesday.setOnClickListener {
            binding.isSelectedTuesday = !(binding.isSelectedTuesday ?: false)
        }
        binding.btnWednesday.setOnClickListener {
            binding.isSelectedWednesday = !(binding.isSelectedWednesday ?: false)
        }
        binding.btnThursday.setOnClickListener {
            binding.isSelectedThursday = !(binding.isSelectedThursday ?: false)
        }
        binding.btnFriday.setOnClickListener {
            binding.isSelectedFriday = !(binding.isSelectedFriday ?: false)
        }
        binding.btnSaturday.setOnClickListener {
            binding.isSelectedSaturday = !(binding.isSelectedSaturday ?: false)
        }
    }

    private fun setBackBtnEvent() {
        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    Navigation.findNavController(requireView()).navigateUp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun hideKeyboard() {
        val im =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        requireActivity().currentFocus?.clearFocus()
    }

}