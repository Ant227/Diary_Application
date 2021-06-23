package com.example.diaryapplication.editentry

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.diaryapplication.Event
import com.example.diaryapplication.database.ProjectDatabase
import com.example.diaryapplication.model.Entry
import com.example.diaryapplication.model.Project
import kotlinx.coroutines.launch
import java.lang.Exception

class EditEntryViewModel(application: Application) : AndroidViewModel(application) {

    private val projectDatabase = ProjectDatabase.getInstance(application)

    private val _entry = MutableLiveData<Entry>()
    val entry: LiveData<Entry>
        get() = _entry

    private val _startTimePickerEvent = MutableLiveData<Event<Unit>>()
    val startTimePickerEvent: LiveData<Event<Unit>>
        get() = _startTimePickerEvent



    private val _endTimePickerEvent = MutableLiveData<Event<Unit>>()
    val endTimePickerEvent: LiveData<Event<Unit>>
        get() = _endTimePickerEvent

    private val _saveEntryEvent = MutableLiveData<Event<Unit>>()
    val saveEntryEvent: LiveData<Event<Unit>>
        get() = _saveEntryEvent

    fun startTimePickerEvent() {
        _startTimePickerEvent.value = Event(Unit)
    }

    fun endTimePickerEvent() {
        _endTimePickerEvent.value = Event(Unit)
    }
    fun saveEntry(){
        _saveEntryEvent.value = Event(Unit)
    }

    fun getEntry(id: Int) {
        viewModelScope.launch {
            _entry.value = projectDatabase.entryDao.getEntry(id)
        }
    }

    fun updateEntry(entry: Entry) {
        viewModelScope.launch {
            try {
                projectDatabase.entryDao.updateEntry(entry)
                Log.i("EditEntryViewModel", "updating Entry.")
            } catch (e: Exception) {
                Log.i("EditEntryViewModel", "Error updating Entry.")
            }
        }
    }
}