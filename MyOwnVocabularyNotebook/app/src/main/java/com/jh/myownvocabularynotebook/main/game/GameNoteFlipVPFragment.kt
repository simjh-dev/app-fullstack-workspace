package com.jh.myownvocabularynotebook.main.game

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import com.jh.myownvocabularynotebook.databinding.FragmentGameNoteFlipVpBinding
import com.jh.myownvocabularynotebook.room.viewmodel.GameNoteItemFlipViewModel
import com.jh.myownvocabularynotebook.util.Const


class GameNoteFlipVPFragment(private val item: GameNoteItemFlipViewModel) : Fragment() {
    private var _binding: FragmentGameNoteFlipVpBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameNoteFlipVpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.item = item
        binding.showKey = item.showKey
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.layoutWrap.setOnClickListener {
            val backAnimation =
                if (item.showKey) ObjectAnimator.ofFloat(
                    binding.layoutValue,
                    Const.TEXT_SCALE_X,
                    0f,
                    1f
                )
                else ObjectAnimator.ofFloat(
                    binding.layoutKey,
                    Const.TEXT_SCALE_X,
                    0f,
                    1f
                )
            backAnimation.interpolator = AccelerateDecelerateInterpolator()
            backAnimation.start()
            item.showKey = !item.showKey
            binding.showKey = item.showKey
        }
    }

}