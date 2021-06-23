package com.example.diaryapplication.mainscreen.entrybottomsheet

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.diaryapplication.Event
import com.example.diaryapplication.database.ProjectDatabase
import com.example.diaryapplication.model.Entry
import com.example.diaryapplication.model.Project
import kotlinx.coroutines.launch
import java.lang.Exception

class AddEntryBottomSheetViewModel(application: Application): AndroidViewModel(application) {

    private val database = ProjectDatabase.getInstance(application)

    private val _addEntryEvent = MutableLiveData<Event<Unit>>()
    val addEntryEvent: LiveData<Event<Unit>>
        get()  = _addEntryEvent


    fun addEntryEvent(){
        _addEntryEvent.value = Event(Unit)
    }


    fun bottomsheetAddEntry(entry: Entry){
        viewModelScope.launch {
            try {
                database.entryDao.insertEntry(entry)
                Log.i("AddEntryBSheetViewModel","inserting Entry.")
            }catch (e: Exception){
                Log.i("AddEntryBSheetViewModel","Error inserting Entry.")
            }
        }
    }

}