package com.kotlinegitim.timer.receviers

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import com.kotlinegitim.timer.database.AlarmDatabase
import com.kotlinegitim.timer.model.Alarm
import com.kotlinegitim.timer.settings.AlarmSettings


class BootRecevier: BroadcastReceiver(){


    var alarm = mutableListOf<Alarm>()
    companion object{

        var isBoot: Boolean = false

    }

    override fun onReceive(context: Context?, intent: Intent?) {

        println("bak bootlandık babe")

        isBoot = true


                val db = Room.databaseBuilder(
                    context!!,
                    AlarmDatabase::class.java,
                    "AlarmDatabase"
                ).build()

                Thread {

                   val alarm = db.AlarmDao().allNotes() as MutableList<Alarm>

                    for (item in alarm) {

                        println(item.toString() + "myitem")

                        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                        val intent = Intent(context, ServiceBroadcastReceiver::class.java)

                        intent.putExtra("setTitle",item.alarmTitle)
                        intent.putExtra("id",item.id)

                        var pendingIntent = PendingIntent.getBroadcast(context,item.id, intent, 0)

                        alarmManager!!.setRepeating(AlarmManager.RTC_WAKEUP, item.time, 1000, pendingIntent)

                        println("alarm is loaded!")

                    }

                }.start()





        /*var intent = Intent(context,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context!!.startActivity(intent)*/


    }

}