package com.jh.myownvocabularynotebook.main.game

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.viewpager2.widget.ViewPager2
import com.jh.myownvocabularynotebook.databinding.LvItemGameNoteExamQuestionBinding
import com.jh.myownvocabularynotebook.util.Const

class GameNoteExamQuestionLVAdapter(
    private val _list: List<String>,
    private val correct: String,
    private val itemCurrentPosition: Int,
    private val viewPager: ViewPager2,
    private val itemCount: Int,
    private val refreshInCompleted: () -> Unit
) : BaseAdapter() {
    private val list = _list
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding =
            if (convertView == null) {
                val binding: LvItemGameNoteExamQuestionBinding =
                    LvItemGameNoteExamQuestionBinding.inflate(
                        LayoutInflater.from(parent?.context),
                        parent,
                        false
                    )
                // tagにインスタンスをセット(convertViewが存在する場合に使い回すため)
                binding.root.tag = binding
                binding
            } else {
                convertView.tag as LvItemGameNoteExamQuestionBinding
            }

        // init UI and set data
        binding.root.layoutParams.height = parent!!.height / count
        binding.isCorrect = true
        binding.questionNumber = "${position + 1}."
        binding.question = list[position]

        binding.layoutWrap.setOnTouchListener { _, _ ->
            // correct
            if (binding.question == correct) {
                if (itemCurrentPosition + 1 == itemCount) {
                    handler.postDelayed({
                        refreshInCompleted()
                    }, Const.DELAY_CORRECT_EVENT)
                } else {
                    handler.postDelayed({
                        viewPager.setCurrentItem(itemCurrentPosition + 1, true)
                    }, Const.DELAY_CORRECT_EVENT)
                }
            }
            // not correct
            else {
                handler.postDelayed({
                    binding.isCorrect = false
                }, Const.DELAY_DISABLE_CLICK_EVENT)
            }
            false
        }

//        // 即時バインド
//        binding.executePendingBindings()
        return binding.root
    }

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

}