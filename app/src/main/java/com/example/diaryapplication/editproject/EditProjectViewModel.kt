package com.example.diaryapplication.editproject

import android.app.Application
import android.util.Log
import androidx.lifecycle.*


import com.example.diaryapplication.Event
import com.example.diaryapplication.database.ProjectDatabase
import com.example.diaryapplication.model.Project
import kotlinx.coroutines.launch
import java.lang.Exception

class EditProjectViewModel(application : Application) : AndroidViewModel(application) {

    private val database = ProjectDatabase.getInstance(application)

    private val _saveProjectEvent = MutableLiveData<Event<Unit>>()
    val saveProjectEvent: LiveData<Event<Unit>>
        get()  = _saveProjectEvent

    private val _startDatePickerEvent = MutableLiveData<Event<Unit>>()
    val startDatePickerEvent: LiveData<Event<Unit>>
        get()  = _startDatePickerEvent

    private val _endDatePickerEvent = MutableLiveData<Event<Unit>>()
    val endDatePickerEvent: LiveData<Event<Unit>>
        get()  = _endDatePickerEvent

    private val _project = MutableLiveData<Project>()

    val project : LiveData<Project> get() = _project


    fun getProject(id: Int){
        viewModelScope.launch {
            val project = database.projectDao.getProject(id)
            _project.value = project
        }
    }

    fun addProjectEvent(){
        _saveProjectEvent.value = Event(Unit)
    }

    fun startDatePickerEvent(){
        _startDatePickerEvent.value = Event(Unit)
    }

    fun endDatePickerEvent(){
        _endDatePickerEvent.value = Event(Unit)
    }

    fun updateProject(project: Project){
        viewModelScope.launch {
            try {
                database.projectDao.insertProject( project)
                Log.i("EditProjectViewModel","updating project.")
            }catch (e: Exception){
                Log.i("EditProjectViewModel","Error updating project.")
            }
        }
    }


}