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


    private val _createProjectEvent = MutableLiveData<Event<Unit>>()
    val createProjectEvent: LiveData<Event<Unit>>
        get()  = _createProjectEvent


    fun getProjectsByArea(area : String) : LiveData<List<Project>>{
        val projects = projectDatabase.projectDao.getProjectsByArea(area)
        return projects
    }
    fun getProjectsByStatus(status : String) : LiveData<List<Project>>{
        val projects = projectDatabase.projectDao.getProjectsByStatus(status)
        return projects
    }

    fun getFilterProjects(area:String,status:String) :LiveData<List<Project>>{
        val projects = projectDatabase.projectDao.getFilterProjects(area,status)
        return projects
    }

    fun createProjectEvent(){
        _createProjectEvent.value = Event(Unit)
    }
}