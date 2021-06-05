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


    fun addProjectEvent(){
        _saveProjectEvent.value = Event(Unit)
    }

    fun addProject(project: Project){
        viewModelScope.launch {
            try {
                database.projectDao.insertProject(project)
                Log.i("EditProjectViewModel","inserting project.")
            }catch (e: Exception){
                Log.i("EditProjectViewModel","Error inserting project.")
            }
        }
    }


}