package com.jh.myownvocabularynotebook.main.add

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.jh.myownvocabularynotebook.R
import com.jh.myownvocabularynotebook.databinding.FragmentMainAddBinding
import com.jh.myownvocabularynotebook.room.entity.Note
import com.jh.myownvocabularynotebook.util.AppMsgUtil
import com.jh.myownvocabularynotebook.util.Const
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainAddFragment : Fragment() {

    private var _binding: FragmentMainAddBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())
    private var prev: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val id = it.getLong(Const.TEXT_NOTE_ID, -1)
            if (id != -1L) {
                setPrevNote(id)
            }
        }
        aboutKeyboard()
        setBackPressEvent()
        setClickEvent()
        setSwitchOnChangedEvent()
        handler.postDelayed({
//            setSelectLanguageSpinner()
        }, Const.DELAY_SHOW_UI)
    }

    private fun setClickEvent() {
        binding.btnSave.setOnClickListener {
            if (isValidate()) {
                val title = binding.etTitle.text!!.toString()
                val memo = binding.etMemo.text.toString()

                lifecycleScope.launch(Dispatchers.IO) {
                    if (prev != null) {
                        val item = Note(
                            prev!!.id,
                            title,
                            memo
                        )
                        (requireActivity().application as com.jh.myownvocabularynotebook.BaseApplication).noteDao.updateNote(item)
                    } else {
                        val item = Note(0, title, memo)
                        (requireActivity().application as com.jh.myownvocabularynotebook.BaseApplication).noteDao.insertNote(item)
                    }
                    lifecycleScope.launch(Dispatchers.Main) {
                        Navigation.findNavController(requireView())
                            .navigate(R.id.action_add_to_edit)
                    }
                }
            }
        }

        binding.btnBack.setOnClickListener {
            // Handle the back button event
            Navigation.findNavController(requireView()).navigateUp()
        }
    }

    private fun setSwitchOnChangedEvent() {
        binding.swiUseTranslation.setOnCheckedChangeListener { _, isChecked ->
            binding.layoutSelectTranslationLanguage.visibility =
                if (isChecked) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun setPrevNote(id: Long) {
        handler.postDelayed({
            lifecycleScope.launch(Dispatchers.IO) {
                val item =
                    (requireActivity().application as com.jh.myownvocabularynotebook.BaseApplication).noteDao.getNoteById(
                        id
                    )
                lifecycleScope.launch(Dispatchers.Main) {
                    binding.etTitle.setText(item.title)
                    binding.etMemo.setText(item.memo)
                    prev = item
                }
            }
        }, Const.DELAY_SHOW_UI)
    }

    private fun isValidate(): Boolean {
        if (binding.etTitle.text == null) return false
        return true
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