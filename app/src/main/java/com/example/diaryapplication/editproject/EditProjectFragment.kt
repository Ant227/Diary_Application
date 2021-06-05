package com.example.diaryapplication.editproject

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.diaryapplication.R
import com.example.diaryapplication.databinding.FragmentEditProjectBinding
import com.example.diaryapplication.model.Project
import com.google.android.material.chip.Chip


class EditProjectFragment : Fragment() {

    private lateinit var binding: FragmentEditProjectBinding
    private var color : Int? = 0
    private var area = ""
    private var status = ""

    private val viewModel: EditProjectViewModel by lazy {
        ViewModelProvider(this).get(EditProjectViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_project,
            container,
            false
        )
        binding.lifecycleOwner = this

        binding.colorChipGroup.setOnCheckedChangeListener{group,checkedId:Int ->
            val chip: Chip? = group.findViewById(checkedId)
            chip?.let {
                 color = chip.chipBackgroundColor?.defaultColor
            }
        }
        binding.areaRadioGroup.setOnCheckedChangeListener{group,checkId ->
            val radio: RadioButton? = group.findViewById(checkId)
            radio?.let {
                    area = radio.text.toString()
            }
        }
        binding.statusRadioGroup.setOnCheckedChangeListener{group,checkId ->
            val radio: RadioButton? = group.findViewById(checkId)
            radio?.let {
                status = radio.text.toString()
            }
        }

        binding.createButton.setOnClickListener {
            val name = binding.projectNameEditText.text.toString()


            if(!TextUtils.isEmpty(name) && !TextUtils.equals(color.toString(), "0")){
                viewModel.addProject(Project(
                    name = name,
                    area = area,
                    status = status,
                    color!!,
                    "",
                    null,
                    null,
                    ""))
                findNavController().popBackStack()
            }
            else{
                Toast.makeText(requireContext(),"Please Enter Project name and Color",Toast.LENGTH_SHORT).show()
            }

        }



        return binding.root
    }

}