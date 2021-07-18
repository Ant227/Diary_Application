package com.example.diaryapplication.mainscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.diaryapplication.BuildConfig
import com.example.diaryapplication.EventObserver
import com.example.diaryapplication.R
import com.example.diaryapplication.SwipeGesture
import com.example.diaryapplication.databinding.FragmentMainScreenBinding
import com.example.diaryapplication.mainscreen.entrybottomsheet.AddEntryBottomSheetFragment
import com.example.diaryapplication.model.Entry
import java.io.File
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


    @SuppressLint("ClickableViewAccessibility", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_screen,
            container,
            false
        )

        binding.lifecycleOwner = this

        adapter = EntryListAdapter(this)
        binding.entryListMain.adapter = adapter




        viewModel.fromToday.observe(viewLifecycleOwner, Observer {
            setupTodayEntries(it)
        })

        viewModel.backUpEvent.observe(viewLifecycleOwner,EventObserver{
            val dbFile1: File = requireContext().getDatabasePath("project_database")
            val dbFile2: File = requireContext().getDatabasePath("project_database-shm")
            val dbFile3: File = requireContext().getDatabasePath("project_database-wal")
            shareFile(dbFile1, dbFile2, dbFile3)
        })
        viewModel.openProjectEvent.observe(viewLifecycleOwner, EventObserver {
            val action =
                MainScreenFragmentDirections.actionMainScreenFragmentToProjectListFragment()
            findNavController().navigate(action)
        })

        viewModel.openBottomSheetEvent.observe(viewLifecycleOwner, EventObserver {
            val string = binding.mainScreenDate.text.toString()
            val date = SimpleDateFormat("yyyy MMM dd EEEE").parse(string)

          val  dayOfMonth   = SimpleDateFormat("dd").format(date).toInt()
          val  month        =  SimpleDateFormat("M").format(date).toInt() - 1
          val  year         =  SimpleDateFormat("yyyy").format(date).toInt()

            val end = Calendar.getInstance()
            end.set(year, month, dayOfMonth)
            end.set(Calendar.HOUR_OF_DAY, 23)
            end.set(Calendar.MINUTE, 59)
            end.set(Calendar.SECOND, 59)
            end.set(Calendar.MILLISECOND, 900)
            val endDate = end.time

            val addEntryBottomSheetFragment = AddEntryBottomSheetFragment(endDate)
            addEntryBottomSheetFragment.show(requireActivity().supportFragmentManager,
                "BottomSheetAddEntryDialog")
        })


        viewModel.getEntriesFromYesterdayEvent.observe(viewLifecycleOwner, EventObserver {
            viewModel.reduceFromToday()
            viewModel.fromToday.observe(viewLifecycleOwner, Observer {
                setupTodayEntries(it)
            })

        })

        viewModel.getEntriesFromTomorrowEvent.observe(viewLifecycleOwner, EventObserver {
            viewModel.increaseFromToday()
            viewModel.fromToday.observe(viewLifecycleOwner, Observer {
                setupTodayEntries(it)
            })


        })

        viewModel.getEntriesFromTodayEvent.observe(viewLifecycleOwner, EventObserver {
            viewModel.today()
            viewModel.fromToday.observe(viewLifecycleOwner, Observer {
                setupTodayEntries(it)
            })

        })

        binding.mainScreenConstraintLayout.setOnTouchListener(object :
            SwipeGesture(requireActivity()) {
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
    private fun setupTodayEntries(fromToday: Int) {
        var todayDate = Date()
        var year = 0
        var month = 0
        var dayOfMonth = 0

        dayOfMonth   = SimpleDateFormat("dd").format(todayDate).toInt() + fromToday
        month        =  SimpleDateFormat("M").format(todayDate).toInt() - 1
        year         =  SimpleDateFormat("yyyy").format(todayDate).toInt()

        val start = Calendar.getInstance()
        start.set(year, month, dayOfMonth)
        start.set(Calendar.HOUR_OF_DAY, 0)
        start.set(Calendar.MINUTE, 0)
        start.set(Calendar.SECOND, 0)
        start.set(Calendar.MILLISECOND, 0)
        val startDate = start.time

        val end = Calendar.getInstance()
        end.set(year, month, dayOfMonth)
        end.set(Calendar.HOUR_OF_DAY, 23)
        end.set(Calendar.MINUTE, 59)
        end.set(Calendar.SECOND, 59)
        end.set(Calendar.MILLISECOND, 900)
        val endDate = end.time

        binding.mainScreenDate.text = SimpleDateFormat("yyyy MMM dd EEEE").format(startDate)
        viewModel.getTEntries(startDate, endDate)

        viewModel.todayEntries.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                binding.mainScreenNoDataTextView.visibility = View.GONE
            }
            if (it.isEmpty()) {
                binding.mainScreenNoDataTextView.visibility = View.VISIBLE
            }
        })



    }

    private fun shareFile(file1: File, file2: File, file3: File) {

        try {

            if(file1.exists()) {
                val uris: ArrayList<Uri> = ArrayList()
                val uri1 = FileProvider.getUriForFile(
                    requireContext(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    file1
                )
                uris.add(uri1)
                val uri2 = FileProvider.getUriForFile(
                    requireContext(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    file2
                )
                uris.add(uri2)
                val uri3 = FileProvider.getUriForFile(
                    requireContext(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    file3
                )
                uris.add(uri3)

                val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.type = "*/*"
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra(Intent.EXTRA_SUBJECT,
                    "BackUp File");
                intent.putExtra(Intent.EXTRA_TEXT, "BackUp File");
                startActivity(intent)
            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.i("MainScreenFragment", "Unable to Share the file :$e")
        }


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

        activity?.findViewById<View>(R.id.mainScreenBackup)?.setOnClickListener {
            viewModel.backupDatabase()
        }


    }

    override fun onClick(entry: Entry) {
        val action = MainScreenFragmentDirections.actionMainScreenFragmentToEntryDetails2(entry.id)
        findNavController().navigate(action)
    }





}