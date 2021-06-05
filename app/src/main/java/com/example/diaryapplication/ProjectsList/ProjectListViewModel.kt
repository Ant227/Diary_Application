package com.example.diaryapplication.ProjectsList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diaryapplication.Event
import com.example.diaryapplication.database.ProjectDatabase
import com.example.diaryapplication.model.Project

class ProjectListViewModel(application: Application) : AndroidViewModel(application){

    private val projectDatabase = ProjectDatabase.getInstance(application)


    val projects : LiveData<List<Project>>
    get() = projectDatabase.projectDao.getAllProjects()


    private val _editProjectEvent = MutableLiveData<Event<Unit>>()
    val editProjectEvent: LiveData<Event<Unit>>
        get()  = _editProjectEvent


    fun getProjectsByArea(area : String) : LiveData<List<Project>>{
        val projects = projectDatabase.projectDao.getProjectsByArea(area)
        return projects
    }



    fun editProjectEvent(){
        _editProjectEvent.value = Event(Unit)
    }
}