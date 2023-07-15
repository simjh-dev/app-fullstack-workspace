package com.example.timerecord.util

import com.example.timerecord.entity.Todo
import com.example.timerecord.entity.TodoHistory
import com.example.timerecord.util.Const.TEMP_TODO_HISTORY_LIST

class RoomDBHelper {

    companion object {
        fun getTodoHistory(item: Todo) : List<TodoHistory> {
            return TEMP_TODO_HISTORY_LIST.filter { history -> history.todoId == item.id }.sortedBy { it.targetDate }
        }
    }
}