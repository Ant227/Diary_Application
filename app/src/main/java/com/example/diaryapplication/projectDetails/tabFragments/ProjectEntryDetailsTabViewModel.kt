package com.example.diaryapplication.projectDetails.tabFragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.diaryapplication.database.ProjectDatabase
import com.example.diaryapplication.model.Entry

class ProjectEntryDetailsTabViewModel(application: Application ,val projectId:Long) : AndroidViewModel(application){

    private val projectDatabase = ProjectDatabase.getInstance(application)

    val entries : LiveData<List<Entry>> = projectDatabase.entryDao.getEntriesOfTheProject(projectId)

}

class ProjectEntryDetailsTabViewModelFactory(
    private val application: Application,
    private val projectId: Long) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectEntryDetailsTabViewModel::class.java)) {
            return ProjectEntryDetailsTabViewModel(  application,projectId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}