package com.jh.myownvocabularynotebook.main.game

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.jh.myownvocabularynotebook.R
import com.jh.myownvocabularynotebook.databinding.FragmentMainGameDetailBinding
import com.jh.myownvocabularynotebook.room.entity.NoteItem
import com.jh.myownvocabularynotebook.util.AppMsgUtil
import com.jh.myownvocabularynotebook.util.Const
import com.jh.myownvocabularynotebook.util.DataUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainGameDetailFragment : Fragment() {

    private var _binding: FragmentMainGameDetailBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainGameDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // init
        initUI()
        setClickEvent()
        setBackPressEvent()
    }

    private fun initUI() {
        binding.isSelectedExam = false
        binding.isFlipRandom = false
        binding.isExamRandom = false
        Glide.with(requireContext()).load(R.drawable.image_flip).into(binding.ivFlip)
        Glide.with(requireContext()).load(R.drawable.image_exam).into(binding.ivExam)
    }

    private fun setClickEvent() {
        binding.swiFlipRandom.setOnCheckedChangeListener { _, isChecked ->
            binding.isFlipRandom = isChecked
        }

        binding.swiExamRandom.setOnCheckedChangeListener { _, isChecked ->
            binding.isExamRandom = isChecked
        }

        binding.btnStart.setOnClickListener {
            binding.isStart = true
            arguments?.let {
                val noteId = it.getLong(Const.TEXT_NOTE_ID, -1)
                handler.postDelayed({
                    lifecycleScope.launch(Dispatchers.IO) {
                        val list =
                            (requireActivity().application as com.jh.myownvocabularynotebook.BaseApplication).noteDao.getNoteItemAllByNoteId(
                                noteId
                            )
                        lifecycleScope.launch(Dispatchers.Main) {
                            if (list.isEmpty()) {
                                AppMsgUtil.showErrMsg(
                                    requireContext().getString(R.string.text_empty_item),
                                    requireActivity()
                                )
                                refresh()
                                return@launch
                            }

                            val isRandom: Boolean? =
                                if (!(binding.isSelectedExam!!)) binding.isFlipRandom // select flip
                                else binding.isExamRandom // select exam

                            if (isRandom != null && isRandom) {
                                setViewPager(list.shuffled())
                            } else {
                                setViewPager(list)
                            }

                        }
                    }
                }, Const.DELAY_SHOW_UI)
            }
        }
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.layoutFlip.setOnClickListener {
            binding.isSelectedExam = false
        }
        binding.layoutExam.setOnClickListener {
            binding.isSelectedExam = true
        }
    }

    private fun setViewPager(list: List<NoteItem>) {
        // prevent to user swipe motion
        binding.viewPager.isUserInputEnabled = !(binding.isSelectedExam!!)
        binding.viewPager.adapter = if (!(binding.isSelectedExam!!)) GameNoteFlipVPAdapter(
            DataUtil.convertToGameNoteItemFlipViewModel(list), requireActivity()
        )
        else GameNoteExamVPAdapter(
            DataUtil.convertToGameNoteItemExamViewModel(list),
            requireActivity(),
            binding.viewPager,
            Navigation.findNavController(requireView())
        )
        binding.showUI = true
    }

    private fun onBackPressed() {
        if (binding.viewPager.adapter != null) {
            refresh()
        } else {
            // Handle the back button event
            Navigation.findNavController(requireView()).navigateUp()
        }
    }

    private fun refresh() {
        arguments?.let {
            val noteId = it.getLong(Const.TEXT_NOTE_ID, -1)
            val bundle = Bundle()
            bundle.putLong(Const.TEXT_NOTE_ID, noteId)
            Navigation.findNavController(requireView())
                .navigate(R.id.action_refresh_game_detail, bundle)
        }
    }

    private fun setBackPressEvent() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
//                    Navigation.findNavController(requireView()).navigateUp()
                    onBackPressed()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}