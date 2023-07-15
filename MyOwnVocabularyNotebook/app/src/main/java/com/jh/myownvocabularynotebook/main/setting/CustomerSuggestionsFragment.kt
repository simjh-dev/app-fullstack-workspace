package com.jh.myownvocabularynotebook.main.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.jh.myownvocabularynotebook.R
import com.jh.myownvocabularynotebook.databinding.FragmentCustomerSuggestionsBinding
import com.jh.myownvocabularynotebook.util.AppMsgUtil


class CustomerSuggestionsFragment : Fragment() {

    private var _binding: FragmentCustomerSuggestionsBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerSuggestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickEvent()
        aboutKeyboard()
        setBackPressEvent()
    }

    private fun setClickEvent() {
        binding.btnBack.setOnClickListener {
            // Handle the back button event
            Navigation.findNavController(requireView()).navigateUp()
        }

        binding.btnSend.setOnClickListener {
            if (isValidate()) {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/html"
                intent.putExtra(
                    Intent.EXTRA_EMAIL,
                    arrayOf(
                        requireContext().resources.getString(R.string.text_developer_email)
                    )
                )
                intent.putExtra(Intent.EXTRA_SUBJECT, binding.etSubject.text.toString()) // 메일 제목
                intent.putExtra(Intent.EXTRA_TEXT, binding.etText.text.toString()) // 메일 내용
                startActivity(intent)
            } else {
                AppMsgUtil.showErrMsg(
                    requireContext().resources.getString(R.string.text_send_email_vaildate_fail),
                    requireActivity()
                )
            }
        }
    }

    private fun isValidate(): Boolean {
        val subject = binding.etSubject.text.toString()
        val text = binding.etText.text.toString()
        return (!subject.isNullOrEmpty()) && (!text.isNullOrEmpty())
    }

    private fun aboutKeyboard() {
        binding.layoutWrap.setOnClickListener {
            if (it !is TextInputEditText) {
                hideKeyboard()
            }
        }
    }

    private fun hideKeyboard() {
        val im =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        requireActivity().currentFocus?.clearFocus()
    }

    private fun setBackPressEvent() {
        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    Navigation.findNavController(requireView()).navigateUp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}