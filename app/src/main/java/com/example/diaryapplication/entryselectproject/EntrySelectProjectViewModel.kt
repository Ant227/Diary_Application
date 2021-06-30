package com.example.diaryapplication.entryselectproject

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.diaryapplication.Event
import com.example.diaryapplication.database.ProjectDatabase
import com.example.diaryapplication.model.Project
import kotlinx.coroutines.launch

class EntrySelectProjectViewModel(application: Application) : AndroidViewModel(application) {
    private val projectDatabase = ProjectDatabase.getInstance(application)

    val projects : LiveData<List<Project>>
        get() = projectDatabase.projectDao.getAllProjects()


    private val _selectedProjectEvent = MutableLiveData<Event<Unit>>()
    val selectedProjectEvent: LiveData<Event<Unit>>
        get()  = _selectedProjectEvent

    fun updateSelectedProject(entryId: Long ,projectId : Long){
        viewModelScope.launch {
            projectDatabase.entryDao.updateEntryProject(entryId,projectId)
        }
    }

    fun selectedProject(){
        _selectedProjectEvent.value = Event(Unit)
    }
}