package com.jh.myownvocabularynotebook.main.edit

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.jh.myownvocabularynotebook.databinding.RvItemEditNoteBinding
import com.jh.myownvocabularynotebook.room.entity.Note
import com.jh.myownvocabularynotebook.room.entity.NoteItem
import com.jh.myownvocabularynotebook.room.viewmodel.NoteItemViewModel

class EditNoteRvAdapter(
    _list: List<NoteItemViewModel>,
    private val noteId: Long,
    private val note: Note
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName
    private var parentContext: Context? = null
    private val handler = Handler(Looper.getMainLooper())
    val list = _list.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            RvItemEditNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        parentContext = parent.context
        return EditNoteRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        (holder as EditNoteRvViewHolder).bind(item)
    }

    override fun getItemCount(): Int = list.size

    fun getResult(_maxId: Long): List<NoteItem> {
        val result = arrayListOf<NoteItem>()
        var maxId = _maxId
        list.forEach { item ->
            val computedId = if (item.id == -1L) ++maxId else item.id
            if (item.key != "") {
                if (item.value != "") {
                    result.add(
                        NoteItem(
                            id = computedId,
                            noteId = item.noteId,
                            key = item.key,
                            value = item.value
                        )
                    )
                }
            }
        }

        return result.sortedBy { it.id }.toList()
    }

    inner class EditNoteRvViewHolder(private val binding: RvItemEditNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NoteItemViewModel) {
            binding.item = item
            setClickEvent()
            setTextChangeEvent()
            aboutKeyboard()
        }

        private fun setClickEvent() {
            binding.btnRemove.setOnClickListener {
                list.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }

        private fun setTextChangeEvent() {
            binding.etKey.addTextChangedListener(
                onTextChanged = { it: CharSequence?, _: Int, _: Int, _: Int ->
                    val item = list[adapterPosition]
                    item.key = it.toString()
                    list[adapterPosition] = item
                })
            binding.etValue.addTextChangedListener(onTextChanged = { it: CharSequence?, _: Int, _: Int, _: Int ->
                val item = list[adapterPosition]
                item.value = it.toString()
                list[adapterPosition] = item
            })
        }

        private fun aboutKeyboard() {
            binding.root.setOnClickListener {
                if (it !is TextInputEditText) {
                    hideKeyboard()
                }
            }
        }

        private fun hideKeyboard() {
            val im =
                parentContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow((parentContext as Activity?)?.currentFocus?.windowToken, 0)
            (parentContext as Activity?)?.currentFocus?.clearFocus()
        }
    }
}