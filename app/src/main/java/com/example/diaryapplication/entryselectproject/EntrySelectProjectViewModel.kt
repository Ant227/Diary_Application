package com.example.diaryapplication.entryselectproject

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.diaryapplication.Event
import com.example.diaryapplication.database.ProjectDatabase
import com.example.diaryapplication.model.Project
import kotlinx.coroutines.launch
import java.lang.Exception

class EntrySelectProjectViewModel(application: Application) : AndroidViewModel(application) {
    private val projectDatabase = ProjectDatabase.getInstance(application)

    val projects : LiveData<List<Project>>
        get() = projectDatabase.projectDao.getAllProjects()


    private val _selectedProjectEvent = MutableLiveData<Event<Unit>>()
    val selectedProjectEvent: LiveData<Event<Unit>>
        get()  = _selectedProjectEvent

    private val _createProjectEvent = MutableLiveData<Event<Unit>>()
    val createProjectEvent: LiveData<Event<Unit>>
        get()  = _createProjectEvent

    fun createProject(){
        _createProjectEvent.value = Event(Unit)
    }

    fun updateSelectedProject(entryId: Long ,projectId : Long){
        viewModelScope.launch {
            projectDatabase.entryDao.updateEntryProject(entryId,projectId)
        }
    }
    fun updateEntry(projectId:Long, projectName:String, projectColor:Int){
        viewModelScope.launch {
            try {
                projectDatabase.entryDao.updateEntryProjectNameColor(projectId,projectName,projectColor)
                Log.i("EditProjectViewModel","updating Entry.")
            }catch (e: Exception){
                Log.i("EditProjectViewModel","Error updating Entry.")
            }
        }
    }
    fun selectedProject(){
        _selectedProjectEvent.value = Event(Unit)
    }
}