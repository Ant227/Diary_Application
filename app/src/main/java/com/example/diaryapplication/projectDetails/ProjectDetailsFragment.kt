package com.example.diaryapplication.projectDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.diaryapplication.R
import com.example.diaryapplication.databinding.FragmentProjectDetailsBinding
import com.example.diaryapplication.projectDetails.tabFragments.ProjectDetailsTabFragment
import com.example.diaryapplication.projectDetails.tabFragments.ProjectEntryDetailsTabFragment
import com.google.android.material.tabs.TabLayoutMediator


class ProjectDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProjectDetailsBinding

    var projectId : Long = 0

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
        setupViewPager()





        return binding.root
    }

    private fun setupViewPager() {
       val adapter = ViewPager2Adapter(requireActivity().supportFragmentManager,lifecycle)
        adapter.addFragment(ProjectDetailsTabFragment(projectId = projectId))
        adapter.addFragment(ProjectEntryDetailsTabFragment())

        binding.projectDetailsViewPager.adapter = adapter
        TabLayoutMediator(binding.projectDetailsTabLayout,binding.projectDetailsViewPager)
        {
                tab,position ->
            when(position){
                0->{
                    tab.text = "INFO"
                }
                1->{
                    tab.text = "Entries"
                }

            }
        }.attach()

    }

}