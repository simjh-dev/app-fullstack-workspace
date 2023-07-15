package com.jh.myownvocabularynotebook.main.game

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import com.jh.myownvocabularynotebook.R
import com.jh.myownvocabularynotebook.databinding.FragmentGameNoteExamVpBinding
import com.jh.myownvocabularynotebook.room.viewmodel.GameNoteItemExamViewModel
import com.jh.myownvocabularynotebook.util.Const


class GameNoteExamVPFragment(
    private val item: GameNoteItemExamViewModel,
    private val position: Int,
    private val viewPager: ViewPager2,
    private val itemCount: Int,
    private val navController: NavController
) : Fragment() {

    private val TAG = this::class.java.simpleName
    private var _binding: FragmentGameNoteExamVpBinding? = null
    private val binding get() = _binding!!
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameNoteExamVpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.item = item
        binding.index = "${position + 1}/$itemCount"
        setClickEvent()
        setListView(item.value)
    }

    private fun setClickEvent() {
        binding.btnItemBack.setOnClickListener {
            if (position - 1 < 0) {

            } else {
                handler.postDelayed({
                    viewPager.setCurrentItem(position - 1, true)
                }, Const.DELAY_CORRECT_EVENT)
            }
        }
    }

    private fun setListView(correct: String) {
        binding.listView.adapter =
            GameNoteExamQuestionLVAdapter(
                item.questions,
                correct,
                position,
                viewPager,
                itemCount,
                ::refreshInCompleted
            )
    }

    private fun refreshInCompleted() {
        val bundle = Bundle()
        bundle.putLong(Const.TEXT_NOTE_ID, item.noteId)
        navController
            .navigate(R.id.action_refresh_game_detail, bundle)
    }
}