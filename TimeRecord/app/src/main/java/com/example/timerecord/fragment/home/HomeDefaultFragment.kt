package com.example.timerecord.fragment.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timerecord.util.Const
import com.example.timerecord.R
import com.example.timerecord.adapter.TodoAdapter
import com.example.timerecord.databinding.FragmentHomeDefaultBinding

class HomeDefaultFragment : Fragment() {

    private var _binding: FragmentHomeDefaultBinding? = null
    private val binding get() = _binding!!

    private var _navController: NavController? = null
    private val navController get() = _navController!!


    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeDefaultBinding.inflate(inflater, container, false)
        _navController = Navigation.findNavController(requireActivity(), R.id.fragment_container)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler.postDelayed({
            initRecyclerView()
            binding.showUI = true
        }, Const.DELAY_SHOW_UI)

        setClickEvent()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = TodoAdapter(Const.TEMP_TODO_LIST, requireActivity())
    }

    private fun setClickEvent() {
        binding.btnAdd.setOnClickListener {
            navController.navigate(R.id.action_default_to_add_todo)
        }
    }

    override fun onResume() {
        super.onResume()
        // add todo list (access room db)
        // addTodo()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
//        binding.showUI = false
    }


}