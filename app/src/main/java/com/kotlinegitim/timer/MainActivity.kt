package com.kotlinegitim.timer

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.kotlinegitim.timer.database.AlarmDatabase
import com.kotlinegitim.timer.fragments.AlarmList
import com.kotlinegitim.timer.fragments.SetAlarm
import com.kotlinegitim.timer.receviers.BootRecevier
import com.kotlinegitim.timer.settings.AlarmSettings
import java.util.*


class MainActivity : AppCompatActivity(){

    lateinit var note : EditText
    lateinit var setAlarms: Button
    lateinit var alarms:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        registerReceiver(BootRecevier(), IntentFilter(Intent.ACTION_BOOT_COMPLETED))


        val setAlarm = SetAlarm()
        val oneBundle = Bundle()
        oneBundle.putString("key1","sendData1")
        setAlarm.arguments = oneBundle
        FragmentManage(setAlarm)

        setAlarms= findViewById(R.id.setAlarm)
        alarms = findViewById(R.id.alarms)

        setAlarms.setBackgroundColor(resources.getColor(R.color.blue))
        alarms.setBackgroundColor(Color.GRAY)


        setAlarms.setOnClickListener {
            oneBundle.putString("key1", UUID.randomUUID().toString() )
            setAlarm.arguments = oneBundle
            FragmentManage(setAlarm)
            alarms.setBackgroundColor(Color.GRAY)
            setAlarms.setBackgroundColor(resources.getColor(R.color.blue))
        }
        alarms.setOnClickListener {
            val alarmObj = AlarmList()
            setAlarms.setBackgroundColor(Color.GRAY)
            alarms.setBackgroundColor(resources.getColor(R.color.blue))
            FragmentManage(alarmObj)
        }


    }


    fun FragmentManage ( fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }


}