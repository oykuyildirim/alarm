package com.kotlinegitim.timer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kotlinegitim.timer.services.AlarmDao
import com.kotlinegitim.timer.model.Alarm

@Database(entities = [Alarm::class], version = 3, exportSchema = false)
abstract class AlarmDatabase : RoomDatabase() {


    abstract fun AlarmDao() : AlarmDao


}