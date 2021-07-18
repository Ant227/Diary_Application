package com.example.diaryapplication.mainscreen

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.diaryapplication.Event
import com.example.diaryapplication.database.ProjectDatabase
import com.example.diaryapplication.model.Entry
import com.example.diaryapplication.model.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*

class MainScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val projectDatabase = ProjectDatabase.getInstance(application)

    val entries : LiveData<List<Entry>>
        get() = projectDatabase.entryDao.getAllEntries()


    lateinit var todayEntries : LiveData<List<Entry>>

    private val _fromToday = MutableLiveData<Int>()
    val fromToday: LiveData<Int>
        get()  = _fromToday

    init {
            _fromToday.value = 0
    }


    private val _openProjectEvent = MutableLiveData<Event<Unit>>()
    val openProjectEvent: LiveData<Event<Unit>>
    get()  = _openProjectEvent

    private val _backUpEvent = MutableLiveData<Event<Unit>>()
    val backUpEvent: LiveData<Event<Unit>>
        get()  = _backUpEvent

    private val _openBottomSheetEvent = MutableLiveData<Event<Unit>>()
    val openBottomSheetEvent: LiveData<Event<Unit>>
        get()  = _openBottomSheetEvent

    private val _getEntriesFromYesterdayEvent = MutableLiveData<Event<Unit>>()
    val getEntriesFromYesterdayEvent: LiveData<Event<Unit>>
        get()  = _getEntriesFromYesterdayEvent

    private val _getEntriesFromTomorrowEvent = MutableLiveData<Event<Unit>>()
    val getEntriesFromTomorrowEvent: LiveData<Event<Unit>>
        get()  = _getEntriesFromTomorrowEvent

    private val _getEntriesFromTodayEvent = MutableLiveData<Event<Unit>>()
    val getEntriesFromTodayEvent: LiveData<Event<Unit>>
        get()  = _getEntriesFromTodayEvent

    fun getTEntries(startDate: Date, endDate: Date){
            todayEntries = projectDatabase.entryDao.getTodayEntries(startDate,endDate)
    }

    fun openProjectList(){
        _openProjectEvent.value = Event(Unit)
    }

    fun backupDatabase(){
        _backUpEvent.value = Event(Unit)
    }

    fun openBottomSheet(){
        _openBottomSheetEvent.value = Event(Unit)
    }

    fun getEntriesFromYesterday(){
        _getEntriesFromYesterdayEvent.value = Event(Unit)
    }

    fun getEntriesFromTomorrow(){
        _getEntriesFromTomorrowEvent.value = Event(Unit)
    }

    fun getEntriesFromToday(){
        _getEntriesFromTodayEvent.value = Event(Unit)
    }

    fun today(){
        _fromToday.value = 0
    }

    fun reduceFromToday(){
        _fromToday.value = _fromToday.value?.minus(1)
    }

    fun increaseFromToday(){
        _fromToday.value = _fromToday.value?.plus(1)
    }
}
