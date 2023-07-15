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
import com.jh.myownvocabularynotebook.databinding.FragmentOpenSourceBinding
import com.jh.myownvocabularynotebook.util.Const
import java.io.ByteArrayOutputStream

class OpenSourceFragment : Fragment() {

    private var _binding: FragmentOpenSourceBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOpenSourceBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler.postDelayed({
            setOpenSourceText()
        }, Const.DELAY_SHOW_UI)
        setClickEvent()
        setBackPressEvent()
    }

    private fun setOpenSourceText() {
        val inputStream = requireContext().resources.openRawResource(R.raw.license)
        val baos = ByteArrayOutputStream()
        var i: Int = inputStream.read()
        while (i != -1) {
            baos.write(i)
            i = inputStream.read()
        }
        val result = baos.toString()
        binding.tvOpenSource.text = result
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