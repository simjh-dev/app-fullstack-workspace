package com.jh.myownvocabularynotebook.main.game

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jh.myownvocabularynotebook.room.viewmodel.GameNoteItemFlipViewModel

class GameNoteFlipVPAdapter(
    private val _list: List<GameNoteItemFlipViewModel>,
    fa: FragmentActivity
) :
    FragmentStateAdapter(fa) {
    private val list: MutableList<GameNoteItemFlipViewModel> = _list.toMutableList()
    override fun getItemCount(): Int = list.size
    override fun createFragment(position: Int): Fragment = GameNoteFlipVPFragment(list[position])
}