package com.example.diaryapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.diaryapplication.model.Entry
import com.example.diaryapplication.model.Project

@Dao
interface EntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: Entry)

    @Update
    suspend fun updateEntry(entry: Entry)

    @Query("SELECT * FROM entry_table")
    fun getAllEntries(): LiveData<List<Entry>>

    @Query("SELECT * FROM entry_table WHERE id = :id")
    suspend fun getEntry(id: Int): Entry
}