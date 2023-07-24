package com.kotlinegitim.timer.settings

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.widget.Toast
import com.kotlinegitim.timer.receviers.ServiceBroadcastReceiver

class AlarmSettings {



    fun setAlarm(act:Activity, id : Int, time:Long, title : String){


            val alarmManager = act.getSystemService(ALARM_SERVICE) as AlarmManager?
            val intent = Intent(act, ServiceBroadcastReceiver::class.java)

            intent.putExtra("id",id)
            intent.putExtra("setTitle",title)
            var pendingIntent = PendingIntent.getBroadcast(act,id, intent, 0)

            println(title+" " +id.toString())
            alarmManager!!.setRepeating(AlarmManager.RTC_WAKEUP, time, 1000, pendingIntent)


            println("alarm is loaded!")

        Toast.makeText(act, "ALARM ON", Toast.LENGTH_SHORT).show()


    }

    fun cancelAlarm(act:Activity, id : Int){


        if (ServiceBroadcastReceiver.id == id) {



                var alarmManager = act.getSystemService(ALARM_SERVICE) as AlarmManager?

                val intent = Intent(act, ServiceBroadcastReceiver::class.java)

                val pendingIntent = PendingIntent.getBroadcast(act, id, intent, 0)

                alarmManager!!.cancel(pendingIntent)
                pendingIntent.cancel()
                ServiceBroadcastReceiver.ringtone!!.stop()



        }

        else{
            println("BU ŞUAN AKTİF DEĞİL SADECE KAPATILDI!")

            val run = Runnable {

                var alarmManager = act.getSystemService(ALARM_SERVICE) as AlarmManager?

                val intent = Intent(act, ServiceBroadcastReceiver::class.java)

                val pendingIntent = PendingIntent.getBroadcast(act, id, intent, 0)

                alarmManager!!.cancel(pendingIntent)
                pendingIntent.cancel()


            }
            Thread(run).start()
            Thread(run).join()


        }


        Toast.makeText(act, "ALARM OFF", Toast.LENGTH_SHORT).show()

    }


}