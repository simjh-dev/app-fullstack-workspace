package com.jh.myownvocabularynotebook.main.setting

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.jh.myownvocabularynotebook.R
import com.jh.myownvocabularynotebook.databinding.FragmentMainSettingBinding
import com.jh.myownvocabularynotebook.util.Const

class MainSettingFragment : Fragment() {

    private var _binding: FragmentMainSettingBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickEvent()
        setBackPressEvent()
    }

    private fun setClickEvent() {
        binding.btnBack.setOnClickListener {
            // Handle the back button event
            Navigation.findNavController(requireView()).navigateUp()
        }

        binding.btnPrivacyPolicy.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Const.TEXT_URL, Const.URL_PRIVACY_POLICY)
            if (isNetworkAvailable()) {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_setting_to_web_view, bundle)
            } else {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_setting_to_mark_down, bundle)
            }
        }

        binding.btnOpensourceLicense.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_setting_to_open_source)
        }

        binding.btnTermsAndConditions.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Const.TEXT_URL, Const.URL_TERMS_AND_CONDITIONS)
            if (isNetworkAvailable()) {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_setting_to_web_view, bundle)
            } else {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_setting_to_mark_down, bundle)
            }
        }
        binding.btnCustomerSuggestions.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_setting_to_customer_suggestions)
        }
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

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager: ConnectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (connectivityManager.activeNetworkInfo != null) {
            connectivityManager.activeNetworkInfo!!.isConnected
        } else {
            false
        }
    }
}