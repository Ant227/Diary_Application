package com.example.diaryapplication.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diaryapplication.model.Project

@Dao
interface ProjectDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: Project)

    @Query("SELECT * FROM project_table")
    fun getAllProjects(): LiveData<List<Project>>

    @Query("SELECT * FROM project_table WHERE id = :id")
    suspend fun getProject(id: Int): Project

    @Query("SELECT * FROM project_table WHERE area = :area")
    fun getProjectsByArea(area : String): LiveData<List<Project>>


}