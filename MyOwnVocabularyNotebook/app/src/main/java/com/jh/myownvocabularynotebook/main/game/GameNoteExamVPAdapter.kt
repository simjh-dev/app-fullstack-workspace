package com.jh.myownvocabularynotebook.main.game

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.jh.myownvocabularynotebook.room.viewmodel.GameNoteItemExamViewModel

class GameNoteExamVPAdapter(
    private val _list: List<GameNoteItemExamViewModel>,
    private val fa: FragmentActivity,
    private val viewPager: ViewPager2,
    private val navController: NavController
) :
    FragmentStateAdapter(fa) {
    private val list: MutableList<GameNoteItemExamViewModel> = _list.toMutableList()
    override fun getItemCount(): Int = list.size
    override fun createFragment(position: Int): Fragment =
        GameNoteExamVPFragment(list[position], position, viewPager, itemCount, navController)
}