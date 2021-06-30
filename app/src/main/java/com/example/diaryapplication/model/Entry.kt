package com.example.diaryapplication.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "entry_table")
data class Entry(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "projectId")val  projectId: Long,
    @ColumnInfo(name = "startTime")val  startTime: Date,
    @ColumnInfo(name = "endTime")val  endTime: Date,
    @ColumnInfo(name = "timeDiff")val  timeDiff: Int,
    @ColumnInfo(name = "startDate")val  startDate: Date,
    @ColumnInfo(name = "marker")val  marker: String,
    @ColumnInfo(name = "note")val  note: String,
) : Parcelable