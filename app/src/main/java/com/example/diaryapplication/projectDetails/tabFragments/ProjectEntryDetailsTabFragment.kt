package com.example.diaryapplication.projectDetails.tabFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.room.util.DBUtil
import com.example.diaryapplication.R
import com.example.diaryapplication.databinding.TabFragmentProjectDetailsBinding
import com.example.diaryapplication.databinding.TabFragmentProjectEntriesBinding
import com.example.diaryapplication.mainscreen.EntryListAdapter
import com.example.diaryapplication.mainscreen.EntryListener
import com.example.diaryapplication.mainscreen.MainScreenFragmentDirections
import com.example.diaryapplication.model.Entry
import com.example.diaryapplication.projectDetails.ProjectDetailsFragmentDirections
import com.example.diaryapplication.projectDetails.ProjectDetailsViewModel
import com.example.diaryapplication.projectDetails.ProjectDetailsViewModelFactory


class ProjectEntryDetailsTabFragment(private val projectId: Long) : Fragment(),ProjectEntryListener {

    private lateinit var binding: TabFragmentProjectEntriesBinding
    private lateinit var viewModel: ProjectEntryDetailsTabViewModel
    private lateinit var adapter: ProjectEntryListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = DataBindingUtil.inflate(inflater,
           R.layout.tab_fragment_project_entries,
           container,
           false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val viewModelFactory = ProjectEntryDetailsTabViewModelFactory(application,projectId)
        viewModel =
            ViewModelProvider(
                this, viewModelFactory).get(ProjectEntryDetailsTabViewModel::class.java)

        adapter = ProjectEntryListAdapter(this)
        binding.tabProjectEntryRecyclerView.adapter = adapter
        viewModel.entries.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }

        })
        return binding.root
    }

    override fun onClick(entry: Entry) {
        val action = ProjectDetailsFragmentDirections.actionProjectDetailsFragmentToEntryDetailsFragment(entry.id)
        findNavController().navigate(action)
    }

}