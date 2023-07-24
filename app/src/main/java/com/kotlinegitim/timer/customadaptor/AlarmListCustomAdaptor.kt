package com.kotlinegitim.timer.customadaptor

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.kotlinegitim.timer.MainActivity
import com.kotlinegitim.timer.database.DBManager
import com.kotlinegitim.timer.database.AlarmDatabase
import com.kotlinegitim.timer.NotePage
import com.kotlinegitim.timer.R
import com.kotlinegitim.timer.model.Alarm
import com.kotlinegitim.timer.receviers.BootRecevier
import com.kotlinegitim.timer.settings.AlarmSettings

class AlarmListCustomAdaptor(private val context: Activity, private val resource: Int, private val objects: MutableList<Alarm>) :
    ArrayAdapter<Alarm>(context, resource, objects) {

    lateinit var clock:TextView
    lateinit var buttonAlarm :Switch


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

       val root = context.layoutInflater.inflate(R.layout.alarm_custom_layout,null,true)



        buttonAlarm = root.findViewById(R.id.switch1)
        clock = root.findViewById(R.id.clock)

         val alarms = objects.get(position)

        clock.text = alarms.calendar


        buttonAlarm.setChecked(alarms.isActive)

        println(alarms.isActive.toString()+"my active")

        if (alarms.alarmTitle.isNullOrEmpty()){

            buttonAlarm.text = "No Title"

        }
        else{
            buttonAlarm.text= alarms.alarmTitle
        }


        buttonAlarm.setOnCheckedChangeListener({ _ , isChecked ->
            if (isChecked) {

            }

            else{

                DBManager().deleteDatabase(alarms,context)
                AlarmSettings().cancelAlarm(context, alarms.id)
                DBManager().GetandSetAllAlarms( context,root.parent as ListView)

            }

        })


        root.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {

                println(alarms.note)
                var intent = Intent(context, NotePage::class.java)
                intent.putExtra("id",alarms.id)
                intent.putExtra("time",alarms.note)
                intent.putExtra("title",alarms.alarmTitle)
                intent.putExtra("note",alarms.note)
                intent.putExtra("active",alarms.isActive)
                intent.putExtra("calendar",alarms.calendar)
                context.startActivity(intent)
            }

        })

        return root
    }


}