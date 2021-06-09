package com.example.diaryapplication.projectDetails.tabFragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.diaryapplication.Event
import com.example.diaryapplication.database.ProjectDatabase
import com.example.diaryapplication.model.Project
import kotlinx.coroutines.launch

class ProjectDetailsTabViewModel(application: Application) : AndroidViewModel(application)
{
    private val database = ProjectDatabase.getInstance(application)


    private val _addProjectEvent = MutableLiveData<Event<Unit>>()
    val addProjectEvent: LiveData<Event<Unit>>
        get()  = _addProjectEvent

    private val _project = MutableLiveData<Project>()
    val project : LiveData<Project>
    get() = _project

    fun getProject(id: Int){
        viewModelScope.launch {
            val project = database.projectDao.getProject(id)
            _project.value = project
        }
    }

    fun addProjectEvent(){
        _addProjectEvent.value = Event(Unit)
    }
}