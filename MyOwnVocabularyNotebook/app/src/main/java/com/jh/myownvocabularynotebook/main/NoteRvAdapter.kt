package com.jh.myownvocabularynotebook.main

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.jh.myownvocabularynotebook.R
import com.jh.myownvocabularynotebook.databinding.RvItemNoteBinding
import com.jh.myownvocabularynotebook.room.entity.Note
import com.jh.myownvocabularynotebook.util.AppMsgUtil
import com.jh.myownvocabularynotebook.util.Const
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NoteRvAdapter(
    private val _list: List<Note>,
    private val parentViewType: Int,
    private val documentTreeLauncher: ActivityResultLauncher<Intent>?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName
    private val list = _list
    private var _context: Context? = null
    private val context get() = _context!!
    var currentId: Long = -1
    var currentTitle: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            RvItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        _context = parent.context
        return NoteRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NoteRvViewHolder).bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class NoteRvViewHolder(private val binding: RvItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note) {
            binding.item = item
            setClickEvent(item)
        }

        private fun setClickEvent(item: Note) {
            binding.layoutWrap.setOnClickListener {
                when (parentViewType) {
                    Const.TYPE_EDIT -> {
                        val bundle = Bundle()
                        bundle.putLong(Const.TEXT_NOTE_ID, item.id)
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.action_edit_to_edit_detail, bundle)
                    }
                    Const.TYPE_GAME -> {
                        val bundle = Bundle()
                        bundle.putLong(Const.TEXT_NOTE_ID, item.id)
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.action_game_to_game_detail, bundle)
                    }
                }
            }

            binding.btnEdit.setOnClickListener {
                val popup = PopupMenu(context, binding.btnEdit)
                when (parentViewType) {
                    Const.TYPE_EDIT -> {
                        popup.menu.add(context.getString(R.string.text_edit))
                        popup.menu.add(context.getString(R.string.text_view))
                        popup.menu.add(context.getString(R.string.text_export))
                        popup.menu.add(context.getString(R.string.text_delete))
                        popup.menu.add(context.getString(R.string.text_cancel))
                    }
                    Const.TYPE_GAME -> {
                        popup.menu.add(context.getString(R.string.text_edit))
                        popup.menu.add(context.getString(R.string.text_view))
                        popup.menu.add(context.getString(R.string.text_cancel))
                    }
                }
                popup.setOnMenuItemClickListener {
                    when (it.title) {
                        context.getString(R.string.text_edit) -> {
                            val bundle = Bundle()
                            bundle.putLong(Const.TEXT_NOTE_ID, item.id)
                            Navigation.findNavController(binding.root)
                                .navigate(R.id.action_edit_or_game_to_add, bundle)
                        }
                        context.getString(R.string.text_view) -> {
                            val bundle = Bundle()
                            bundle.putLong(Const.TEXT_NOTE_ID, item.id)
                            Navigation.findNavController(binding.root)
                                .navigate(R.id.action_edit_or_game_to_view, bundle)
                        }
                        context.getString(R.string.text_delete) -> {
                            showDeletePrompt(item)
                        }
                        context.getString(R.string.text_export) -> {
                            // android 13 above
                            if (SDK_INT > Build.VERSION_CODES.S_V2) processExportExcelAboveAndroid13()
                            // android 13 under
                            else processExportExcelUnderAndroid13()
                        }
                        context.getString(R.string.text_cancel) -> {
                            popup.dismiss()
                        }
                    }
                    false
                }
                popup.show()
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        private fun showDeletePrompt(item: Note) {
            val builder = AlertDialog.Builder(context)
            val alert = builder
                .setMessage("Do you want to DELETE \"${item.title}\" Note? \n※If deleted, it cannot be reversed.")
                .setCancelable(true)
                .setPositiveButton(context.getString(R.string.text_yes)) { _, _ ->
                    GlobalScope.launch(Dispatchers.IO) {
                        ((context as Activity).application as com.jh.myownvocabularynotebook.BaseApplication).noteDao.deleteNote(
                            item
                        )
                        GlobalScope.launch(Dispatchers.Main) {
                            AppMsgUtil.showMsg(
                                context.getString(R.string.text_delete_note_success),
                                (context as Activity)
                            )
                            Navigation.findNavController(binding.root)
                                .navigate(R.id.action_refresh_edit)
                        }
                    }

                }
                .setNegativeButton(context.getString(R.string.text_no)) { _, _ ->
                }.create()
            alert.setTitle("Check to delete note")
            alert.show()
        }

        private fun processExportExcelAboveAndroid13() {
            binding.item?.id?.let { id ->
                binding.item?.title?.let { title ->
                    documentTreeLauncher?.let { launcher ->
                        currentId = id
                        currentTitle = title
                        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
                        launcher.launch(intent)
                    }
                }
            }
        }

        private fun processExportExcelUnderAndroid13() {
            // **Need to check if it works**
            binding.item?.id?.let { id ->
                binding.item?.title?.let { title ->
                    documentTreeLauncher?.let { launcher ->
                        currentId = id
                        currentTitle = title
                        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
                        launcher.launch(intent)
                    }
                }
            }
        }
    }
}