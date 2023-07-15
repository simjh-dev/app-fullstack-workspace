package com.jh.myownvocabularynotebook.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.jh.myownvocabularynotebook.R
import com.jh.myownvocabularynotebook.databinding.FragmentMainDefaultBinding

class MainDefaultFragment : Fragment() {

    private var _binding: FragmentMainDefaultBinding? = null
    private val binding get() = _binding!!

    private var _navController: NavController? = null
    private val navController get() = _navController!!

    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainDefaultBinding.inflate(inflater, container, false)
        _navController = Navigation.findNavController(requireActivity(), R.id.fragment_container)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.cardViewAdd.setOnClickListener {
            navController.navigate(R.id.action_default_to_add)
        }

        binding.cardViewEdit.setOnClickListener {
            navController.navigate(R.id.action_default_to_edit)
        }

        binding.cardViewGame.setOnClickListener {
            navController.navigate(R.id.action_default_to_game)
        }

        binding.cardViewSetting.setOnClickListener {
            navController.navigate(R.id.action_default_to_setting)
        }
    }
}