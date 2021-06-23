package com.example.diaryapplication.mainscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.diaryapplication.EventObserver
import com.example.diaryapplication.ProjectsList.ProjectListAdapter
import com.example.diaryapplication.ProjectsList.ProjectListFragmentDirections
import com.example.diaryapplication.ProjectsList.bottomsheet.BottomSheetFragment
import com.example.diaryapplication.R
import com.example.diaryapplication.databinding.FragmentMainScreenBinding
import com.example.diaryapplication.mainscreen.entrybottomsheet.AddEntryBottomSheetFragment
import com.example.diaryapplication.model.Entry


class MainScreenFragment : Fragment(),EntryListener {

            private lateinit var binding: FragmentMainScreenBinding

    private val viewModel: MainScreenViewModel by lazy {
        ViewModelProvider(this).get(MainScreenViewModel::class.java)
    }
    private lateinit var adapter: EntryListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClick()

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

        adapter = EntryListAdapter(this)
        binding.entryListMain.adapter = adapter

        viewModel.entries.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }

        })

        viewModel.openProjectEvent.observe(viewLifecycleOwner,EventObserver{
            val action = MainScreenFragmentDirections.actionMainScreenFragmentToProjectListFragment()
            findNavController().navigate(action)
        })

        viewModel.openBottomSheetEvent.observe(viewLifecycleOwner,EventObserver{
            val addEntryBottomSheetFragment = AddEntryBottomSheetFragment()
            addEntryBottomSheetFragment
                .show(requireActivity().supportFragmentManager, "BottomSheetAddEntryDialog")
        })



        return binding.root
    }

    private fun setupClick() {
        activity?.findViewById<View>(R.id.addProjectImageButton)?.setOnClickListener {
            viewModel.openProjectList()
        }

        activity?.findViewById<View>(R.id.mainScreenFAB)?.setOnClickListener {
            viewModel.openBottomSheet()
        }

    }

    override fun onClick(entry: Entry) {
        val action = MainScreenFragmentDirections.actionMainScreenFragmentToEntryDetails2(entry.id)
        findNavController().navigate(action)
    }
}