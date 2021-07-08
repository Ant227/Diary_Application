package com.example.diaryapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.diaryapplication.model.Entry
import java.util.*

@Dao
interface EntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: Entry)

    @Update
    suspend fun updateEntry(entry: Entry)

    @Query("UPDATE entry_table SET projectId = :projectId WHERE id = :entryId")
   suspend fun updateEntryProject(entryId: Long, projectId: Long)

    @Query("UPDATE entry_table SET projectName = :projectName , projectColor =:projectColor WHERE projectId = :projectId")
    suspend fun updateEntryProjectNameColor(projectId: Long, projectName: String, projectColor: Int)

    @Query("SELECT * FROM entry_table")
    fun getAllEntries(): LiveData<List<Entry>>

    @Query("SELECT * FROM entry_table WHERE projectId = :projectId")
    fun getEntriesOfTheProject(projectId: Long) : LiveData<List<Entry>>

    @Query("SELECT COUNT(*) FROM entry_table WHERE projectId = :projectId")
    fun getNumberOfEntriesOfTheProject(projectId: Long) : LiveData<Int>

    @Query("SELECT * FROM entry_table WHERE id = :id")
    suspend fun getEntry(id: Long): Entry

    @Query("SELECT * FROM entry_table WHERE startDate BETWEEN :dayst AND :dayet")
    fun getTodayEntries(dayst: Date, dayet: Date): LiveData<List<Entry>>


}