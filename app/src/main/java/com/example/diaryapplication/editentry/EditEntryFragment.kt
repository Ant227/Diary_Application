package com.example.diaryapplication.editentry

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.diaryapplication.EventObserver
import com.example.diaryapplication.R
import com.example.diaryapplication.databinding.FragmentEditEntryBinding
import com.example.diaryapplication.model.Entry
import com.google.android.material.timepicker.TimeFormat
import java.lang.Exception
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*


class EditEntryFragment : Fragment() {


    private lateinit var startTime: Date
    private lateinit var endTime: Date
    private var marker = ""




    private lateinit var binding: FragmentEditEntryBinding

    private val viewModel: EditEntryViewModel by lazy {
        ViewModelProvider(this).get(EditEntryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_entry,
            container,
            false
        )
        binding.lifecycleOwner = this

        val bundle = EditEntryFragmentArgs.fromBundle(requireArguments())
        val entryId = bundle.argEntryId
        viewModel.getEntry(entryId)
        viewModel.entry.observe(viewLifecycleOwner, Observer {
            binding.entry = it
            startTime = it.startTime
            endTime = it.endTime


            calculateTimeDiff(startTime,endTime)
            setUpCheckBox()


        })

        binding.editEntryFromTitle.setOnClickListener {
            viewModel.startTimePickerEvent()
        }
        binding.editEntryToTitle.setOnClickListener {
            viewModel.endTimePickerEvent()
        }
        binding.editEntrySaveButton.setOnClickListener {
            viewModel.saveEntry()
        }

        setUpTimePickerDialog()

        viewModel.saveEntryEvent.observe(viewLifecycleOwner,EventObserver{
            val name = binding.editEntryName.text.toString()
            val projectId = 0
            val timeDiff = calculateTimeDiff(startTime,endTime)
            val note = binding.editEntryComment.text.toString()

            viewModel.updateEntry(Entry(
                id = entryId,
                name = name,
                projectId = projectId,
                startTime = startTime,
                endTime = endTime,
                startDate = binding.entry!!.startDate,
                timeDiff = timeDiff,
                marker = marker,
                note = note
            ))
            Toast.makeText(
                requireContext(),
                "Entry Updated",
                Toast.LENGTH_SHORT
            ).show()
        })





        return binding.root
    }

    private fun setUpCheckBox() {

        checkbox(binding.editEntryCheckBoxStar,"S")
        checkbox(binding.editEntryCheckBoxNew,"N")
        checkbox(binding.editEntryCheckBoxFlag,"F")

    }

    private fun checkbox(checkBox: CheckBox, char: String) {
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                marker += char
            } else {
                if (marker.contains(char)) {
                    marker = marker.replace(char, "")
                }
            }
            Toast.makeText(requireContext(), marker, Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculateTimeDiff(startTime: Date, endTime: Date) : Int{

        val startingHours = SimpleDateFormat("HH").format(startTime).toInt()
        val endingHours = SimpleDateFormat("HH").format(endTime).toInt()

        val startingMinutes = SimpleDateFormat("mm").format(startTime).toInt()
        val endingMinutes = SimpleDateFormat("mm").format(endTime).toInt()

        val start = startingHours * 60 + startingMinutes
        val end = endingHours * 60 + endingMinutes

        val diff  = end - start
        val diffHours = diff/60
        val diffMinutes = diff % 60

        var finalHours = diffHours.toString()
        var finalMinutes = diffMinutes.toString()

        if(diffHours < 10){
            finalHours = "0$diffHours"
        }
        if(diffMinutes < 10){
            finalMinutes = "0$diffMinutes"
        }

        binding.editEntryTime.text = "$finalHours:$finalMinutes"

        return diff


    }

    private fun setUpTimePickerDialog() {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        val startTimeListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            calender.set(year, month, day, hourOfDay, minute)
            startTime = calender.time
            binding.editEntryFrom.text = SimpleDateFormat("h:mm a").format(startTime)
            calculateTimeDiff(startTime,endTime)


        }

        val endTimeListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            calender.set(year, month, day, hourOfDay, minute)
            endTime = calender.time
            binding.editEntryTo.text = SimpleDateFormat("h:mm a").format(endTime)

            calculateTimeDiff(startTime,endTime)

        }

        viewModel.startTimePickerEvent.observe(viewLifecycleOwner, EventObserver {
            TimePickerDialog(
                requireContext(),
                startTimeListener,
                calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE),
                false
            ).show()


        })

        viewModel.endTimePickerEvent.observe(viewLifecycleOwner, EventObserver {
            TimePickerDialog(
                requireContext(),
                endTimeListener,
                calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE),
                false
            ).show()
        })


    }


}