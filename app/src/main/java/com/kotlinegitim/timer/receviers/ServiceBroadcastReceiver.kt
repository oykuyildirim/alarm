package com.kotlinegitim.timer.receviers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.kotlinegitim.timer.MainActivity
import com.kotlinegitim.timer.R


class ServiceBroadcastReceiver : BroadcastReceiver() {


    companion object {
        var ringtone: Ringtone? = null
        var id = 0
    }

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    private val channelId = "i.apps.notifications"
    private val description = "alarm notification"
    lateinit var builder: Notification.Builder



    @RequiresApi(api = Build.VERSION_CODES.Q)
    override fun onReceive(context: Context, intent: Intent?) {



        var title = intent!!.getStringExtra("setTitle")
        println(title)

        id = intent!!.getIntExtra("id",0)

        var alarmUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }


        SetNotification(context,title.toString())

        ringtone = RingtoneManager.getRingtone(context, alarmUri)

        ringtone!!.play()



    }

    fun SetNotification(context:Context, title:String){

        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val intent = Intent(context, MainActivity::class.java)


        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(context, channelId)
                .setContentTitle("ALARM NOTTIFICATION")
                .setContentText("Alarm ${title} is actived")
                .setSmallIcon(R.drawable.alarm_icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources,
                    R.drawable.ic_launcher_background
                ))
                .setContentIntent(pendingIntent)
        } else {

            builder = Notification.Builder(context)
                .setContentTitle("ALARM NOTTIFICATION")
                .setContentText("Alarm ${title} is actived")
                .setSmallIcon(R.drawable.alarm_icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources,
                    R.drawable.ic_launcher_background
                ))
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())
    }


}

