package com.kotlinegitim.timer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.Toast
import androidx.room.Room
import com.kotlinegitim.timer.database.AlarmDatabase
import com.kotlinegitim.timer.model.Alarm
import com.kotlinegitim.timer.settings.AlarmSettings
import java.util.*

class NotePage : AppCompatActivity(),TextToSpeech.OnInitListener{

    private var tts: TextToSpeech? = null

    lateinit var speak : Button
    lateinit var delete : Button
    lateinit var note : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_page)

        note = findViewById(R.id.note)

        speak = findViewById(R.id.read)

        tts = TextToSpeech(this, this)

        var id= intent.getIntExtra("id",0)
        var time = intent.getLongExtra("time",0)
        var title = intent.getStringExtra("title")
        var noteText = intent.getStringExtra("note")
        var active =intent.getBooleanExtra("active",false)
        var calendar =intent.getStringExtra("calendar")




        println(noteText+"kddjdjk")

        if (noteText.isNullOrEmpty()){
            noteText = "No Entered Note"
        }
        note.text = noteText

        speak.setOnClickListener{

            speakOut(noteText.toString())

        }






    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                println("The Language not supported!")
            } else {

            }
        }
    }
    private fun speakOut(text :String) {

        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    public override fun onDestroy() {

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }


    fun deleteDatabase(alarm:Alarm){

        val db = Room.databaseBuilder(
            this,
            AlarmDatabase::class.java,
            "AlarmDatabase"
        ).build()
        val run = Runnable {


            db.AlarmDao().Delete(alarm)


        }
        Thread(run).start()


        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(this,"Alarm is deleted!",Toast.LENGTH_SHORT).show()

    }
}