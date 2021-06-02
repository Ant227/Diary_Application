package com.example.diaryapplication.ProjectsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diaryapplication.Event

class ProjectListViewModel : ViewModel(){

    private val _editProjectEvent = MutableLiveData<Event<Unit>>()
    val editProjectEvent: LiveData<Event<Unit>>
        get()  = _editProjectEvent


    fun editProjectEvent(){
        _editProjectEvent.value = Event(Unit)
    }
}