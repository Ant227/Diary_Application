package com.example.diaryapplication.entryselectproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.room.util.DBUtil
import com.example.diaryapplication.EventObserver
import com.example.diaryapplication.ProjectsList.ProjectListAdapter
import com.example.diaryapplication.ProjectsList.bottomsheet.BottomSheetFragment
import com.example.diaryapplication.R
import com.example.diaryapplication.databinding.FragmentEditProjectBinding
import com.example.diaryapplication.databinding.FragmentEntrySelectProjectBinding
import com.example.diaryapplication.editentry.EditEntryFragmentArgs
import com.example.diaryapplication.model.Project


class EntrySelectProjectFragment : Fragment(),EntrySelectProjectListener {

    private lateinit var binding: FragmentEntrySelectProjectBinding

    private val viewModel : EntrySelectProjectViewModel by lazy {
        ViewModelProvider(this).get(EntrySelectProjectViewModel::class.java)
    }

    private lateinit var adapter: EntrySelectProjectListAdapter

    private var projectId : Long = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_entry_select_project,
            container
            , false)

        binding.lifecycleOwner = this

        val bundle = EntrySelectProjectFragmentArgs.fromBundle(requireArguments())
        val entryId = bundle.argsEntryId

        adapter = EntrySelectProjectListAdapter(this)
        binding.entrySelectProjectRecyclerView.adapter = adapter


        viewModel.projects.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

     viewModel.selectedProjectEvent.observe(viewLifecycleOwner, EventObserver{
            val action = EntrySelectProjectFragmentDirections
                .actionEntrySelectProjectFragmentToEditEntryFragment(entryId)
            viewModel.updateSelectedProject(entryId,projectId)
            findNavController().navigate(action)
        })

        binding.entrySelectProjectFab.setOnClickListener {
            viewModel.createProject()
        }

        viewModel.createProjectEvent.observe(viewLifecycleOwner,EventObserver{
            val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.show(requireActivity().supportFragmentManager, "BottomSheetAddProjectDialog")
        })


        return binding.root
    }

    override fun onClick(project: Project) {
        projectId = project.id
        viewModel.selectedProject()
    }
}