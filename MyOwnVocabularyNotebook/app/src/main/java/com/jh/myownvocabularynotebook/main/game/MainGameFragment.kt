package com.jh.myownvocabularynotebook.main.game

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.jh.myownvocabularynotebook.R
import com.jh.myownvocabularynotebook.databinding.FragmentMainGameBinding
import com.jh.myownvocabularynotebook.main.NoteRvAdapter
import com.jh.myownvocabularynotebook.room.entity.Note
import com.jh.myownvocabularynotebook.util.Const
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainGameFragment : Fragment() {

    private var _binding: FragmentMainGameBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler.postDelayed({
            lifecycleScope.launch(Dispatchers.IO) {
                val list = (requireActivity().application as com.jh.myownvocabularynotebook.BaseApplication).noteDao.getNoteAll()
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
        binding.recyclerView.adapter = NoteRvAdapter(list, Const.TYPE_GAME, null)
        binding.showUI = true
    }

    private fun setClickEvent() {
        binding.btnEditNote.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_game_to_edit)
        }
        binding.btnBack.setOnClickListener {
            // Handle the back button event
            Navigation.findNavController(requireView()).navigateUp()
        }
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