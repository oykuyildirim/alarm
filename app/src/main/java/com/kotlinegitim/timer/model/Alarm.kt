package com.kotlinegitim.timer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AlarmSet")

data class Alarm(@PrimaryKey val id:Int,
                 val time:Long,
                 val alarmTitle : String? = "No Title",
                 val  note : String? = "No Entered Note",
                 val isActive : Boolean,
                 val calendar : String
)

