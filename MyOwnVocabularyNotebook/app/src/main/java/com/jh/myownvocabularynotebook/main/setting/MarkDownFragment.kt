package com.jh.myownvocabularynotebook.main.setting

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.jh.myownvocabularynotebook.R
import com.jh.myownvocabularynotebook.databinding.FragmentMarkDownBinding
import com.jh.myownvocabularynotebook.util.Const
import io.noties.markwon.Markwon

class MarkDownFragment : Fragment() {

    private var _binding: FragmentMarkDownBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMarkDownBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler.postDelayed({
            setMarkDown()
        }, Const.DELAY_SHOW_UI)
        setClickEvent()
        setBackPressEvent()
    }

    private fun setMarkDown() {
        arguments?.let {
            val url = it.getString(Const.TEXT_URL)
            val markwon = Markwon.create(requireContext())
            val markdown = if (url!! == Const.URL_PRIVACY_POLICY) {
                markwon.toMarkdown(requireContext().resources.getString(R.string.text_privacy_policy_md))
            } else {
                markwon.toMarkdown(requireContext().resources.getString(R.string.text_terms_and_conditions_md))
            }
            markwon.setParsedMarkdown(binding.markDownView, markdown)
        }

        binding.showUI = true
    }

    private fun setClickEvent() {
        binding.btnBack.setOnClickListener {
            // Handle the back button event
            Navigation.findNavController(requireView()).navigateUp()
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
}