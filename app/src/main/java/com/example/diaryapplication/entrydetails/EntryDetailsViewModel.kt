package com.example.diaryapplication.entrydetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.diaryapplication.Event
import com.example.diaryapplication.database.ProjectDatabase
import com.example.diaryapplication.model.Entry
import kotlinx.coroutines.launch

class EntryDetailsViewModel(application:Application) : AndroidViewModel(application) {

    private val projectDatabase = ProjectDatabase.getInstance(application)

    private val _entry = MutableLiveData<Entry>()
    val entry: LiveData<Entry>
        get()  = _entry

    private val _openEditEntryEvent = MutableLiveData<Event<Unit>>()
    val openEditEntryEvent: LiveData<Event<Unit>>
        get()  = _openEditEntryEvent


    fun openEditEntry(){
        _openEditEntryEvent.value = Event(Unit)
    }

    fun getEntry(id: Int){
        viewModelScope.launch {
                _entry.value = projectDatabase.entryDao.getEntry(id)
        }
    }



}