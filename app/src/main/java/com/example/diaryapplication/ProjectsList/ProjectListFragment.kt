package com.example.diaryapplication.ProjectsList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.diaryapplication.EventObserver
import com.example.diaryapplication.R
import com.example.diaryapplication.databinding.FragmentProjectListBinding
import com.example.diaryapplication.model.Project
import com.google.android.material.chip.Chip


class ProjectListFragment : Fragment() {

    private lateinit var binding: FragmentProjectListBinding
    private val viewModel: ProjectListViewModel by lazy {
        ViewModelProvider(this).get(ProjectListViewModel::class.java)
    }

    private lateinit var adapter: ProjectListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_project_list,
            container,
            false
        )
        binding.lifecycleOwner = this

        binding.addProjectFab.setOnClickListener {
            viewModel.editProjectEvent()
        }

        viewModel.editProjectEvent.observe(viewLifecycleOwner, EventObserver {
            val action =
                ProjectListFragmentDirections.actionProjectListFragmentToEditProjectFragment()
            findNavController().navigate(action)
        })

        adapter = ProjectListAdapter()
        binding.projectRecyclerView.adapter = adapter

        observeProjects()


        //  getFilterAreaProjects()
        filter()




        return binding.root
    }



    private fun filter() {
        val chipGroup = binding.projectlistAreaChipgroup
        val nameList = mutableListOf<String>()

        for (index in 0 until chipGroup.childCount) {
            val chip:Chip = chipGroup.getChildAt(index) as Chip

            chip.setOnCheckedChangeListener{view, isChecked ->
                if (isChecked){
                    nameList.add(view.text.toString())
                }else{
                    nameList.remove(view.text.toString())
                }

                if (nameList.isNotEmpty()) {
                    val projectList = mutableListOf<Project>()
                    for (name in nameList) {
                        val projects = viewModel.getProjectsByArea(name)
                        projects.observe(viewLifecycleOwner, Observer {
                            projectList.addAll(it)
                            projectList.sortBy { it.id }
                            adapter.submitList(projectList)
                        })
                    }
                }

                if (nameList.isEmpty()) {
                    observeProjects()
                }
            }
        }


    }


    private fun observeProjects() {
        viewModel.projects.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

}