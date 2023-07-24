package com.kotlinegitim.timer.services

import androidx.room.*
import com.kotlinegitim.timer.model.Alarm

@Dao

interface AlarmDao {

    @Query("select * from AlarmSet")
    fun allNotes() : List<Alarm>

    @Insert
    fun Insert( note: Alarm) : Long

    @Delete
    fun Delete( note: Alarm)

    @Update
    fun Update( note: Alarm)

}