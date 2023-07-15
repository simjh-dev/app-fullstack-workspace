package com.jh.myownvocabularynotebook.util

import android.util.Log
import com.jh.myownvocabularynotebook.room.entity.NoteItem
import com.jh.myownvocabularynotebook.room.viewmodel.GameNoteItemExamViewModel
import com.jh.myownvocabularynotebook.room.viewmodel.GameNoteItemFlipViewModel
import com.jh.myownvocabularynotebook.room.viewmodel.NoteItemViewModel
import com.jh.myownvocabularynotebook.util.Const.MAX_SIZE_QUESTION
import org.apache.poi.ss.usermodel.Workbook

class DataUtil {

    companion object {

        private val TAG = this::class.java.simpleName

        fun convertToNoteItemViewModel(list: List<NoteItem>): List<NoteItemViewModel> {
            val result = arrayListOf<NoteItemViewModel>()
            for (item in list) {
                result.add(
                    NoteItemViewModel(
                        id = item.id,
                        noteId = item.noteId,
                        key = item.key,
                        value = item.value
                    )
                )
            }
            return result
        }

        fun convertToGameNoteItemFlipViewModel(list: List<NoteItem>): List<GameNoteItemFlipViewModel> {
            val result = arrayListOf<GameNoteItemFlipViewModel>()
            for (item in list) {
                result.add(
                    GameNoteItemFlipViewModel(
                        id = item.id,
                        noteId = item.noteId,
                        key = item.key,
                        value = item.value,
                        showKey = true
                    )
                )
            }
            return result
        }

        fun convertToGameNoteItemExamViewModel(list: List<NoteItem>): List<GameNoteItemExamViewModel> {
            val result = arrayListOf<GameNoteItemExamViewModel>()
            if (list.size < MAX_SIZE_QUESTION) {
                val questions = arrayListOf<String>()
                for (item in list) {
                    questions.add(item.value)
                }
                for (item in list) {
                    result.add(
                        GameNoteItemExamViewModel(
                            id = item.id,
                            noteId = item.noteId,
                            key = item.key,
                            value = item.value,
                            questions = questions
                        )
                    )
                }
            } else {
                for (item in list) {
                    val questions = arrayListOf<String>()
                    questions.add(item.value)
                    while (questions.size < MAX_SIZE_QUESTION) {
                        getAnotherValue(list.shuffled(), questions)?.let { anotherValue ->
                            questions.add(anotherValue)
                        }
                    }
                    result.add(
                        GameNoteItemExamViewModel(
                            id = item.id,
                            noteId = item.noteId,
                            key = item.key,
                            value = item.value,
                            questions = questions.shuffled()
                        )
                    )
                }
            }


            return result
        }

        fun convertExcelToItems(workbook: Workbook, noteId: Long): List<NoteItem>? {
            val sheet = workbook.getSheet(Const.TEXT_RESULT)
            val keyTitle = sheet.getRow(0).getCell(0).toString()
            val valueTitle = sheet.getRow(0).getCell(1).toString()
            return if (keyTitle == Const.TEXT_KEY && valueTitle == Const.TEXT_VALUE) {
                val result = arrayListOf<NoteItem>()
                val keyCellIdx = 0
                val valueCellIdx = 1
                for (i in 1..sheet.lastRowNum) {
                    val row = sheet.getRow(i)
                    row.getCell(keyCellIdx)?.let { key ->
                        row.getCell(valueCellIdx)?.let { value ->
                            result.add(
                                NoteItem(
                                    noteId = noteId,
                                    key = key.toString(),
                                    value = value.toString()
                                )
                            )
                        }
                    }
                }
                result
            } else null
        }

        private fun getAnotherValue(
            shuffled: List<NoteItem>,
            prevQuestions: List<String>
        ): String? {
            for (item in shuffled) {
                if (!prevQuestions.contains(item.value)) return item.value
            }
            return null
        }
    }
}