package com.example.diaryapplication.mainscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.diaryapplication.EventObserver
import com.example.diaryapplication.R
import com.example.diaryapplication.databinding.FragmentMainScreenBinding


class MainScreenFragment : Fragment() {

            private lateinit var binding: FragmentMainScreenBinding

    private val viewModel: MainScreenViewModel by lazy {
        ViewModelProvider(this).get(MainScreenViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAddProject()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
        R.layout.fragment_main_screen,
        container,
        false)

        binding.lifecycleOwner = this

        viewModel.addProjectEvent.observe(viewLifecycleOwner,EventObserver{
            val action = MainScreenFragmentDirections.actionMainScreenFragmentToProjectListFragment()
            findNavController().navigate(action)
        })



        return binding.root
    }

    private fun setupAddProject() {
        activity?.findViewById<View>(R.id.addProjectImageButton)?.setOnClickListener {
            viewModel.addProjectEvent()
        }

    }
}