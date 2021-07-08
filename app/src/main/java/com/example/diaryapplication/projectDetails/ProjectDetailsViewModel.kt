package com.example.diaryapplication.projectDetails

import android.app.Application
import androidx.lifecycle.*
import com.example.diaryapplication.database.ProjectDatabase
import com.example.diaryapplication.model.Entry

class ProjectDetailsViewModel(application: Application, val projectId : Long) : AndroidViewModel(application) {

    private val database = ProjectDatabase.getInstance(application)
    val numberOfEntries : LiveData<Int>

    init {
        numberOfEntries= database.entryDao.getNumberOfEntriesOfTheProject(projectId)
    }




}

class ProjectDetailsViewModelFactory(
    private val application: Application,
    private val projectId: Long) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectDetailsViewModel::class.java)) {
            return ProjectDetailsViewModel(  application,projectId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}