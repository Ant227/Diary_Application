package com.example.diaryapplication.ProjectsList

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
import com.example.diaryapplication.databinding.FragmentProjectListBinding


class ProjectListFragment : Fragment() {

 private lateinit var binding: FragmentProjectListBinding
 private val viewModel : ProjectListViewModel by lazy {
     ViewModelProvider(this).get(ProjectListViewModel::class.java)
 }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_project_list,
            container,
        false)
        binding.lifecycleOwner = this

        binding.addProjectFab.setOnClickListener{
            viewModel.editProjectEvent()
        }

        viewModel.editProjectEvent.observe(viewLifecycleOwner,EventObserver{
            val action = ProjectListFragmentDirections.actionProjectListFragmentToEditProjectFragment()
            findNavController().navigate(action)
        })


        return binding.root
    }




}