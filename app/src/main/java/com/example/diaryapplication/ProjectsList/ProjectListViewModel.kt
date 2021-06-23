package com.example.diaryapplication.ProjectsList

import android.app.Application
import androidx.lifecycle.*
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



    fun getProjectsByArea(area : String) : LiveData<List<Project>> {
            return projectDatabase.projectDao.getProjectsByArea(area)
    }

    fun getProjectsByStatus(status : String) : LiveData<List<Project>>{
        return projectDatabase.projectDao.getProjectsByStatus(status)
    }

    fun getFilterProjects(area:String,status:String) :LiveData<List<Project>>{
        return projectDatabase.projectDao.getFilterProjects(area,status)
    }

    fun createProjectEvent(){
        _createProjectEvent.value = Event(Unit)
    }


}