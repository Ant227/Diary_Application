package com.example.diaryapplication.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diaryapplication.Event

class MainScreenViewModel : ViewModel() {

    private val _addProjectEvent = MutableLiveData<Event<Unit>>()
    val addProjectEvent: LiveData<Event<Unit>>
    get()  = _addProjectEvent


    fun addProjectEvent(){
        _addProjectEvent.value = Event(Unit)
    }
}