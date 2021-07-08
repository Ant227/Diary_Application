package com.example.diaryapplication.entrydetails

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
import com.example.diaryapplication.R
import com.example.diaryapplication.databinding.FragmentEntryDetailsBinding
import com.example.diaryapplication.mainscreen.MainScreenFragmentDirections


class EntryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEntryDetailsBinding

    private val viewModel: EntryDetailsViewModel by lazy {
        ViewModelProvider(this).get(EntryDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = DataBindingUtil.inflate(inflater,
           R.layout.fragment_entry_details,
           container,
           false)
        binding.lifecycleOwner = this

        val bundle = EntryDetailsFragmentArgs.fromBundle(requireArguments())
        val entryId = bundle.argsEntryId
        viewModel.getEntry(entryId)
        binding.entryDetailsFab.setOnClickListener {
            viewModel.openEditEntry()
        }

        viewModel.entry.observe(viewLifecycleOwner, Observer {
            binding.entry = it
                       viewModel.getProject(it.projectId)
        })

        viewModel.project.observe(viewLifecycleOwner, Observer {
            binding.project = it

        })

        viewModel.openEditEntryEvent.observe(viewLifecycleOwner, EventObserver {
            val action = EntryDetailsFragmentDirections.actionEntryDetailsFragmentToEditEntryFragment(entryId)
            findNavController().navigate(action)
        })



        return binding.root
    }


}