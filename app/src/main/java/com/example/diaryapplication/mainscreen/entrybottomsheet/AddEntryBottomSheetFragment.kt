package com.example.diaryapplication.mainscreen.entrybottomsheet

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.diaryapplication.EventObserver
import com.example.diaryapplication.ProjectsList.bottomsheet.BottomSheetViewModel
import com.example.diaryapplication.R
import com.example.diaryapplication.databinding.FragmentAddEntryBottomSheetBinding
import com.example.diaryapplication.model.Entry
import com.example.diaryapplication.model.Project
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class AddEntryBottomSheetFragment(private val date : Date) : BottomSheetDialogFragment(){

    private lateinit var binding : FragmentAddEntryBottomSheetBinding

    private val viewModel: AddEntryBottomSheetViewModel by lazy {
        ViewModelProvider(this).get(AddEntryBottomSheetViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_entry_bottom_sheet,
            container,
            false
        )

        binding.lifecycleOwner = this

        binding.bottomsheetAddEntryButton.setOnClickListener{
            viewModel.addEntryEvent()

        }

        viewModel.addEntryEvent.observe(viewLifecycleOwner,EventObserver{
          var name = binding.bottomsheetEntryName.text.toString()

            if(!TextUtils.isEmpty(name)){
                viewModel.bottomsheetAddEntry(
                    Entry(
                        name = name,
                       projectId =  -1,
                        projectName = "",
                        projectColor = null,
                     startTime =    Date(),
                      endTime =   Date(),
                        timeDiff = 0,
                      startDate =   date,
                       marker = "",
                        note = ""
                        )
                )

            }
            else{
                Toast.makeText(requireContext(),"Please Enter Entry Name", Toast.LENGTH_SHORT).show()
            }
            binding.bottomsheetEntryName.setText("")
        })


        return binding.root
    }


}