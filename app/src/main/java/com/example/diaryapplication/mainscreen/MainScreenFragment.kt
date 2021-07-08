package com.example.diaryapplication.mainscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.diaryapplication.EventObserver
import com.example.diaryapplication.R
import com.example.diaryapplication.SwipeGesture
import com.example.diaryapplication.databinding.FragmentMainScreenBinding
import com.example.diaryapplication.mainscreen.entrybottomsheet.AddEntryBottomSheetFragment
import com.example.diaryapplication.model.Entry
import java.text.SimpleDateFormat
import java.util.*


class MainScreenFragment : Fragment(),EntryListener {

            private lateinit var binding: FragmentMainScreenBinding

    private val viewModel: MainScreenViewModel by lazy {
        ViewModelProvider(this).get(MainScreenViewModel::class.java)
    }
    private lateinit var adapter: EntryListAdapter

        

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClick()

    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
        R.layout.fragment_main_screen,
        container,
        false)

        binding.lifecycleOwner = this

        adapter = EntryListAdapter(this)
        binding.entryListMain.adapter = adapter




        viewModel.fromToday.observe(viewLifecycleOwner, Observer {
            setupTodayEntries(it)
        })


        viewModel.openProjectEvent.observe(viewLifecycleOwner,EventObserver{
            val action = MainScreenFragmentDirections.actionMainScreenFragmentToProjectListFragment()
            findNavController().navigate(action)
        })

        viewModel.openBottomSheetEvent.observe(viewLifecycleOwner,EventObserver{
            val addEntryBottomSheetFragment = AddEntryBottomSheetFragment()
            addEntryBottomSheetFragment
                .show(requireActivity().supportFragmentManager, "BottomSheetAddEntryDialog")
        })


        viewModel.getEntriesFromYesterdayEvent.observe(viewLifecycleOwner,EventObserver{
            viewModel.reduceFromToday()
            viewModel.fromToday.observe(viewLifecycleOwner, Observer {
                setupTodayEntries(it)
            })

        })

        viewModel.getEntriesFromTomorrowEvent.observe(viewLifecycleOwner,EventObserver{
            viewModel.increaseFromToday()
            viewModel.fromToday.observe(viewLifecycleOwner, Observer {
                setupTodayEntries(it)
            })


        })

        viewModel.getEntriesFromTodayEvent.observe(viewLifecycleOwner,EventObserver{
            viewModel.today()
            viewModel.fromToday.observe(viewLifecycleOwner, Observer {
                setupTodayEntries(it)
            })

        })

        binding.mainScreenConstraintLayout.setOnTouchListener(object : SwipeGesture(requireActivity()){
            override fun onSwipeRight() {
                viewModel.reduceFromToday()
                viewModel.fromToday.observe(viewLifecycleOwner, Observer {
                    setupTodayEntries(it)
                })
            }

            override fun onSwipeLeft() {
                viewModel.increaseFromToday()
                viewModel.fromToday.observe(viewLifecycleOwner, Observer {
                    setupTodayEntries(it)
                })
            }


        })

        return binding.root
    }




    @SuppressLint("SimpleDateFormat")
    private fun setupTodayEntries(fromToday : Int) {
        var todayDate = Date()
        var year = 0
        var month = 0
        var dayOfMonth = 0

        dayOfMonth   = SimpleDateFormat("dd").format(todayDate).toInt() + fromToday
        month        =  SimpleDateFormat("M").format(todayDate).toInt() - 1
        year         =  SimpleDateFormat("yyyy").format(todayDate).toInt()

        val start = Calendar.getInstance()
        start.set(year,month,dayOfMonth)
        start.set(Calendar.HOUR_OF_DAY, 0)
        start.set(Calendar.MINUTE, 0)
        start.set(Calendar.SECOND, 0)
        start.set(Calendar.MILLISECOND, 0)
        val startDate = start.time

        val end = Calendar.getInstance()
        end.set(year,month,dayOfMonth)
        end.set(Calendar.HOUR_OF_DAY, 23)
        end.set(Calendar.MINUTE, 59)
        end.set(Calendar.SECOND, 59)
        end.set(Calendar.MILLISECOND, 900)
        val endDate = end.time

        binding.mainScreenDate.text = SimpleDateFormat("yyyy MMM dd EEEE").format(startDate)


        viewModel.getTEntries(startDate,endDate)

        viewModel.todayEntries.observe(viewLifecycleOwner, Observer {
           it?.let {
               adapter.submitList(it)
               binding.mainScreenNoDataTextView.visibility = View.GONE
           }
            if (it.isEmpty()){
                binding.mainScreenNoDataTextView.visibility = View.VISIBLE
            }
        })

    }

    private fun setupClick() {
        activity?.findViewById<View>(R.id.addProjectImageButton)?.setOnClickListener {
            viewModel.openProjectList()
        }

        activity?.findViewById<View>(R.id.mainScreenFAB)?.setOnClickListener {
            viewModel.openBottomSheet()
        }

        activity?.findViewById<View>(R.id.mainScreenDateLeft)?.setOnClickListener {
            viewModel.getEntriesFromYesterday()
        }

        activity?.findViewById<View>(R.id.mainScreenDateRight)?.setOnClickListener {
            viewModel.getEntriesFromTomorrow()
        }

        activity?.findViewById<View>(R.id.mainScreenDateToday)?.setOnClickListener {
            viewModel.getEntriesFromToday()
        }


    }

    override fun onClick(entry: Entry) {
        val action = MainScreenFragmentDirections.actionMainScreenFragmentToEntryDetails2(entry.id)
        findNavController().navigate(action)
    }





}