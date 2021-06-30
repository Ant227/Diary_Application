package com.example.diaryapplication.projectDetails.tabFragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.diaryapplication.R
import com.example.diaryapplication.databinding.TabFragmentProjectDetailsBinding
import com.example.diaryapplication.editproject.EditProjectViewModel
import com.example.diaryapplication.projectDetails.ProjectDetailsFragmentDirections


class ProjectDetailsTabFragment(private val projectId: Long) : Fragment() {

    private lateinit var binding: TabFragmentProjectDetailsBinding

    private val viewModel: ProjectDetailsTabViewModel by lazy {
        ViewModelProvider(this).get(ProjectDetailsTabViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.tab_fragment_project_details,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.projectDetailsComment.movementMethod = ScrollingMovementMethod()

        viewModel.getProject(projectId)

        viewModel.project.observe(viewLifecycleOwner, Observer {
            binding.project = it
        })
        viewModel.addProjectEvent.observe(viewLifecycleOwner, Observer {

        })


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.projectDetailsFab.setOnClickListener{
            val action = ProjectDetailsFragmentDirections.actionProjectDetailsFragmentToEditProjectFragment2(projectId)
            findNavController().navigate(action)
        }
    }

}