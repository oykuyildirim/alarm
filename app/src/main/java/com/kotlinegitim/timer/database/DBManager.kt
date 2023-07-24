package com.kotlinegitim.timer.database

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.ListView
import android.widget.Toast
import androidx.room.Room
import com.kotlinegitim.timer.MainActivity
import com.kotlinegitim.timer.R
import com.kotlinegitim.timer.customadaptor.AlarmListCustomAdaptor
import com.kotlinegitim.timer.model.Alarm
import com.kotlinegitim.timer.settings.AlarmSettings


class DBManager {

   var list = mutableListOf<Alarm>()

    fun setBootedAlarms(act:Activity){
        val db = Room.databaseBuilder(
            act,
            AlarmDatabase::class.java,
            "AlarmDatabase"
        ).build()

        Thread {

            val ls = db.AlarmDao().allNotes()
            for (item in ls) {

                println(item.toString() + "myitem")

                AlarmSettings().setAlarm(
                    act,
                    item.id,
                    item.time,
                    item.alarmTitle!!
                )

                println("alarm is loaded!")

            }

        }.start()
    }


    fun deleteDatabase(alarm:Alarm, act : Activity){

        list.remove(alarm)
        println("Delete from database")
        val db = Room.databaseBuilder(
            act,
            AlarmDatabase::class.java,
            "AlarmDatabase"
        ).build()
        val run = Runnable {


            db.AlarmDao().Delete(alarm)


        }
        Thread(run).start()
        Thread(run).join()


        //GetandSetAllAlarms(act,listAlarm)


        /*var intent = Intent(act, MainActivity::class.java)
        act.startActivity(intent)*/
        /*Handler(Looper.getMainLooper()).postDelayed({
            Toast.makeText(act,"Alarm is deleted!", Toast.LENGTH_SHORT).show()
        }, 3000)*/


    }

    fun UpdateAlarm(alarms: Alarm, isActive:Boolean,act:Activity){

        val db = Room.databaseBuilder(
            act,
            AlarmDatabase::class.java,
            "AlarmDatabase"
        ).build()

        val run = Runnable {

            var alarm = Alarm(alarms.id,
                alarms.time,
                alarms.alarmTitle,
                alarms.note,
                isActive,
                alarms.calendar

            )
            db.AlarmDao().Update(alarm)
        }

        Thread(run).start()



    }

   fun setAlarm(act:Activity, alarm:Alarm ){

       val db = Room.databaseBuilder(
           act,
           AlarmDatabase::class.java,
           "AlarmDatabase"
       ).build()
       val run = Runnable {

           db.AlarmDao().Insert(alarm)

       }

       Thread(run).start()
       Thread(run).join()


   }

    fun GetandSetAllAlarms(act: Activity,listAlarm:ListView){

        val db = Room.databaseBuilder(
            act,
            AlarmDatabase::class.java,
            "AlarmDatabase"
        ).build()

        list.removeAll(list)

        val run = Runnable{

            val ls = db.AlarmDao().allNotes()
            for ( item in ls ) {

                list.add(item)
                println(list)

            }

            act.runOnUiThread {

                val adapter = AlarmListCustomAdaptor(act, R.layout.alarm_custom_layout,
                    list
                )
                listAlarm.adapter = adapter
                adapter.notifyDataSetChanged()


            }

        }
        Thread(run).start()
        Thread(run).join()




    }


}