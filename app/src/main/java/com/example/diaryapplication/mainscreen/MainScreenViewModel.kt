package com.example.diaryapplication.mainscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diaryapplication.Event
import com.example.diaryapplication.database.ProjectDatabase
import com.example.diaryapplication.model.Entry
import com.example.diaryapplication.model.Project

class MainScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val projectDatabase = ProjectDatabase.getInstance(application)

    val entries : LiveData<List<Entry>>
        get() = projectDatabase.entryDao.getAllEntries()

    private val _openProjectEvent = MutableLiveData<Event<Unit>>()
    val openProjectEvent: LiveData<Event<Unit>>
    get()  = _openProjectEvent

    private val _openBottomSheetEvent = MutableLiveData<Event<Unit>>()
    val openBottomSheetEvent: LiveData<Event<Unit>>
        get()  = _openBottomSheetEvent


    fun openProjectList(){
        _openProjectEvent.value = Event(Unit)
    }

    fun openBottomSheet(){
        _openBottomSheetEvent.value = Event(Unit)
    }
}