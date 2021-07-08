package com.example.diaryapplication.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.diaryapplication.model.Project
import java.util.*

@Dao
interface ProjectDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: Project)


    @Query("SELECT * FROM project_table")
    fun getAllProjects(): LiveData<List<Project>>

    @Query("SELECT * FROM project_table WHERE id = :id")
    suspend fun getProject(id: Long): Project

    @Query("SELECT name FROM project_table WHERE id = :id")
    suspend fun getProjectName(id: Long): String


    @Query("SELECT * FROM project_table WHERE area = :area")
    fun getProjectsByArea(area : String): LiveData<List<Project>>

    @Query("SELECT * FROM project_table WHERE status = :status")
    fun getProjectsByStatus(status : String): LiveData<List<Project>>

    @Query("SELECT * FROM project_table WHERE area = :area AND status = :status")
    fun getFilterProjects(area: String,status : String): LiveData<List<Project>>


}