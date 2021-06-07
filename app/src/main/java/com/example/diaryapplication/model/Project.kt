package com.example.diaryapplication.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*
@Parcelize
@Entity(tableName = "project_table")
data class Project(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "area")val  area: String,
    @ColumnInfo(name = "status")val  status: String,
    @ColumnInfo(name = "color")val  color: Int?,
    @ColumnInfo(name = "tag")val  tag: String,
    @ColumnInfo(name = "startDate")val  startDate: Date?,
    @ColumnInfo(name = "endDate")val  endDate: Date?,
    @ColumnInfo(name = "comment")val  comment: String,
    ) : Parcelable{
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}