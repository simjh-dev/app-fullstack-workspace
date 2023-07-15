package com.jh.myownvocabularynotebook.main.edit

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.jh.myownvocabularynotebook.R
import com.jh.myownvocabularynotebook.databinding.FragmentMainEditBinding
import com.jh.myownvocabularynotebook.main.NoteRvAdapter
import com.jh.myownvocabularynotebook.room.entity.Note
import com.jh.myownvocabularynotebook.util.AppMsgUtil
import com.jh.myownvocabularynotebook.util.Const
import com.jh.myownvocabularynotebook.util.Const.MIME_TYPE_XLSX
import com.jh.myownvocabularynotebook.util.Const.TEXT_XLSX
import com.jh.myownvocabularynotebook.util.DateUtil
import com.jh.myownvocabularynotebook.util.FileUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream

class MainEditFragment : Fragment() {

    private var _binding: FragmentMainEditBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())
    private val documentTreeLauncher: ActivityResultLauncher<Intent> = getDocumentTreeLauncher()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler.postDelayed({
            lifecycleScope.launch(Dispatchers.IO) {
                val list =
                    (requireActivity().application as com.jh.myownvocabularynotebook.BaseApplication).noteDao.getNoteAll()
                lifecycleScope.launch(Dispatchers.Main) {
                    setRecyclerView(list)
                }
            }
        }, Const.DELAY_SHOW_UI)
        setClickEvent()
        setBackPressEvent()
    }

    private fun setRecyclerView(list: List<Note>) {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter =
            NoteRvAdapter(list, Const.TYPE_EDIT, documentTreeLauncher)
        binding.showUI = true
    }

    private fun setClickEvent() {
        binding.btnAddNote.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_edit_or_game_to_add)
        }
        binding.btnBack.setOnClickListener {
            // Handle the back button event
            Navigation.findNavController(requireView()).navigateUp()
        }
    }

    private fun getDocumentTreeLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                result.data?.let {
                    it.data?.let { uri ->
                        val takeFlags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        requireContext().contentResolver.takePersistableUriPermission(
                            uri,
                            takeFlags
                        )
                        lifecycleScope.launch(Dispatchers.IO) {

                            val id = (binding.recyclerView.adapter as NoteRvAdapter).currentId
                            val title = (binding.recyclerView.adapter as NoteRvAdapter).currentTitle

                            if (id != -1L) {
                                val list =
                                    (requireActivity().application as com.jh.myownvocabularynotebook.BaseApplication).noteDao.getNoteItemAllByNoteId(
                                        id
                                    )
                                lifecycleScope.launch(Dispatchers.Main) {
                                    val fileName = "$title-${DateUtil.getCurrentTime()}.$TEXT_XLSX"
                                    val isSuccess =
                                        saveExcel(uri, fileName, FileUtil.makeExcel(list))
                                    if (isSuccess) AppMsgUtil.showMsg(
                                        "$fileName ${
                                            requireContext().getString(
                                                R.string.text_save_success
                                            )
                                        }", requireActivity()
                                    )
                                    else AppMsgUtil.showErrMsg(
                                        "$fileName ${
                                            requireContext().getString(
                                                R.string.text_save_failed
                                            )
                                        }", requireActivity()
                                    )

                                    // refresh
                                    (binding.recyclerView.adapter as NoteRvAdapter).currentId = -1
                                    (binding.recyclerView.adapter as NoteRvAdapter).currentTitle =
                                        ""
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun saveExcel(uri: Uri, fileName: String, workbook: XSSFWorkbook): Boolean {
        DocumentFile.fromTreeUri(requireContext(), uri)?.let { directory ->
            directory.createFile(MIME_TYPE_XLSX, fileName)?.let { file ->
                val pfd = requireContext().contentResolver.openFileDescriptor(file.uri, "w")
                if (pfd != null) {
                    val fos = FileOutputStream(pfd.fileDescriptor)
                    try {
                        workbook.write(fos)
                        return true
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        pfd.close()
                        fos.flush()
                        fos.close()
                    }
                }
            }
        }
        return false
    }

    private fun setBackPressEvent() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    Navigation.findNavController(requireView()).navigateUp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}