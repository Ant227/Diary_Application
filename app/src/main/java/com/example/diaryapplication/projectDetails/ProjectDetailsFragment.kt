package com.example.diaryapplication.projectDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.diaryapplication.R
import com.example.diaryapplication.databinding.FragmentProjectDetailsBinding
import com.example.diaryapplication.projectDetails.tabFragments.ProjectDetailsTabFragment
import com.example.diaryapplication.projectDetails.tabFragments.ProjectEntryDetailsTabFragment
import com.google.android.material.tabs.TabLayoutMediator


class ProjectDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProjectDetailsBinding
    private lateinit var viewModel: ProjectDetailsViewModel

     private var projectId : Long = 0
    private var  numberOfEntries : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_project_details,
            container,
            false
        )
        binding.lifecycleOwner = this
        val bundle = ProjectDetailsFragmentArgs.fromBundle(requireArguments())
        projectId = bundle.argId

        val application = requireNotNull(this.activity).application
        val viewModelFactory = ProjectDetailsViewModelFactory(application,projectId)
        viewModel =
            ViewModelProvider(
                this, viewModelFactory).get(ProjectDetailsViewModel::class.java)

            setupViewPager()





        return binding.root
    }

    private fun setupViewPager() {
       val adapter = ViewPager2Adapter(requireActivity().supportFragmentManager,lifecycle)
        adapter.addFragment(ProjectDetailsTabFragment( projectId))
        adapter.addFragment(ProjectEntryDetailsTabFragment(projectId))

        binding.projectDetailsViewPager.adapter = adapter
        TabLayoutMediator(binding.projectDetailsTabLayout,binding.projectDetailsViewPager)
        {
                tab,position ->
            when(position){
                0->{
                    tab.text = "INFO"
                }
                1->{
                    viewModel.numberOfEntries.observe(viewLifecycleOwner, Observer {
                        numberOfEntries  = it
                        tab.text = "Entries($numberOfEntries)"
                    })


                }

            }
        }.attach()

    }

}