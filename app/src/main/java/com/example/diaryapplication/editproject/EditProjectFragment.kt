package com.example.diaryapplication.editproject

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.diaryapplication.EventObserver
import com.example.diaryapplication.R
import androidx.lifecycle.Observer
import com.example.diaryapplication.databinding.FragmentEditProjectBinding
import com.example.diaryapplication.model.Project
import com.google.android.material.chip.Chip
import java.text.SimpleDateFormat
import java.util.*



class EditProjectFragment : Fragment(){

    private lateinit var binding: FragmentEditProjectBinding
    private var color: Int? = null
    private var area = ""
    private var status = ""
    private lateinit var  lastEntryDate : Date

    private var startDay = "0"
    private var startMonth = "0"
    private var startYear = "0"
     var startDate = Date()

    private var endDay = "0"
    private var endMonth = "0"
    private var endYear = "0"
    private  var endDate :Date? = null


    private val viewModel: EditProjectViewModel by lazy {
        ViewModelProvider(this).get(EditProjectViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
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

        val bundle = EditProjectFragmentArgs.fromBundle(requireArguments())
        val projectId = bundle.argProjectId
        viewModel.getProject(projectId)

        binding.lifecycleOwner = this

        viewModel.project.observe(viewLifecycleOwner, Observer { it ->
            binding.project = it
            color = it.color
            area = it.area
            status = it.status
            lastEntryDate = it.lastEntryDate

            //setup date
            it.startDate?.let {
               startDay          = SimpleDateFormat("dd").format(it)
               startMonth  =  (SimpleDateFormat("M").format(it).toInt() -1).toString()
               startYear         =  SimpleDateFormat("yyyy").format(it)
                startDate = it
            }
            it.endDate?.let {
                endDay          = SimpleDateFormat("dd").format(it)
                endMonth  =  (SimpleDateFormat("M").format(it).toInt() -1).toString()
                endYear         =  SimpleDateFormat("yyyy").format(it)
                endDate = it
            }
            if(it.endDate == null){
                endDay =   startDay
                endMonth=  startMonth
                endYear=   startYear
            }

            getSetProjectValue()
        })


        viewModel.saveProjectEvent.observe(viewLifecycleOwner, EventObserver {
            val name = binding.projectNameEditText.text.toString()
            val comment = binding.editProjectComment.text.toString()

            if (!TextUtils.isEmpty(name) && color != null) {
                viewModel.updateProject(
                    Project(
                         projectId,
                         name,
                         area,
                         status,
                        color,
                        "",
                        startDate,
                        endDate,
                       comment,
                        lastEntryDate
                    )
                )

                viewModel.updateEntry(projectId,name,color!!)
                findNavController().navigate(EditProjectFragmentDirections.
                actionEditProjectFragmentToProjectDetailsFragment(projectId))
            } else {
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(
                        requireContext(),
                        "Please enter project name.",
                        Toast.LENGTH_SHORT
                    ).show()
                }else if(TextUtils.isEmpty(name) && color == null){
                    Toast.makeText(
                        requireContext(),
                        "Please enter project name and select project color",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else{
                    Toast.makeText(
                        requireContext(),
                        "Please select project color",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        })
        setupDatePicker()



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createButton.setOnClickListener {
            viewModel.addProjectEvent()
        }

        binding.startDatePicker.setOnClickListener {
            viewModel.startDatePickerEvent()
        }

        binding.endDatePicker.setOnClickListener {
            viewModel.endDatePickerEvent()
        }


    }

    private fun getSetProjectValue() {
        val areaRadioGroup = binding.areaRadioGroup
        val statusRadioGroup = binding.statusRadioGroup

        for (index in 0 until areaRadioGroup.childCount) {

            val radioButton: RadioButton = areaRadioGroup.getChildAt(index) as RadioButton
            if (TextUtils.equals(radioButton.text, area)){
                radioButton.isChecked = true
            }
        }

        for (index in 0 until statusRadioGroup.childCount) {

            val radioButton: RadioButton = statusRadioGroup.getChildAt(index) as RadioButton
            if (TextUtils.equals(radioButton.text, status)){
                radioButton.isChecked = true
            }
        }

        binding.colorChipGroup.setOnCheckedChangeListener { group, checkedId: Int ->
            val chip: Chip? = group.findViewById(checkedId)
            chip?.let {
                color = chip.chipBackgroundColor?.defaultColor
            }
        }
        binding.areaRadioGroup.setOnCheckedChangeListener { group, checkId ->
            val radio: RadioButton? = group.findViewById(checkId)
            radio?.let {
                area = radio.text.toString()
            }
        }
        binding.statusRadioGroup.setOnCheckedChangeListener { group, checkId ->
            val radio: RadioButton? = group.findViewById(checkId)
            radio?.let {
                status = radio.text.toString()
                if(TextUtils.equals(status,"Ongoing")){
                    endDate = null

                    binding.endDatePicker.text = ""
                }
                if(TextUtils.equals(status,"Hold") || TextUtils.equals(status,"Done")){

                    if(endDate == null){
                        endDate = Date()
                        val formattedDate =   SimpleDateFormat("dd.MM.yyyy").format(endDate)
                        binding.endDatePicker.text = formattedDate
                    }

                }

            }
        }

    }


    @SuppressLint("SimpleDateFormat")
    private fun setupDatePicker(){
        val startDateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal.set(year,month,dayOfMonth)
            startDate = cal.time
            binding.startDatePicker.text = SimpleDateFormat("dd.MM.yyyy").format(startDate)
            startDay = dayOfMonth.toString()
            startMonth = month.toString()
            startYear = year.toString()


        }
        val endDateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal.set(year,month,dayOfMonth)
            endDate = cal.time
            binding.endDatePicker.text = SimpleDateFormat("dd.MM.yyyy").format(endDate!!)
            endDay = dayOfMonth.toString()
            endMonth = month.toString()
            endYear = year.toString()
        }

        viewModel.startDatePickerEvent.observe(viewLifecycleOwner,EventObserver{
            Log.i("Date","$startMonth")
            DatePickerDialog(requireContext(),
                startDateListener,
                startYear.toInt(),
                startMonth.toInt() ,
                startDay.toInt()).show()

        })
        viewModel.endDatePickerEvent.observe(viewLifecycleOwner,EventObserver{
            Log.i("Date","$endMonth")
            DatePickerDialog(requireContext(),
                endDateListener,
                endYear.toInt(),
                endMonth.toInt() ,
                endDay.toInt()).show()
        })
    }




}



