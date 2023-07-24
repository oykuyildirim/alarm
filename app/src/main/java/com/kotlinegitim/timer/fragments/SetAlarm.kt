package com.kotlinegitim.timer.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.kotlinegitim.timer.database.DBManager
import com.kotlinegitim.timer.database.AlarmDatabase
import com.kotlinegitim.timer.settings.AlarmSettings
import com.kotlinegitim.timer.R
import com.kotlinegitim.timer.model.Alarm
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SetAlarm : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var alarmTimePicker: TimePicker? = null
    lateinit var setBtn : Button
    lateinit var title : EditText
    lateinit var note : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view: View = inflater.inflate(R.layout.set_alarm_fragment_layout, container, false)



        val db = Room.databaseBuilder(
            requireContext(),
            AlarmDatabase::class.java,
            "AlarmDatabase"
        ).build()


        alarmTimePicker = view. findViewById(R.id.timePicker)

        title = view.findViewById(R.id.editTextTextPersonName)
        note = view.findViewById(R.id.editTextTextPersonName2)

        setBtn = view.findViewById(R.id.setBtn)

        setBtn.setOnClickListener{
            OnSwitchClicked(db)
        }

        return view
    }

    fun OnSwitchClicked(db: AlarmDatabase) {
        var time: Long
        val random = Random()
        val number = random.nextInt()

        Toast.makeText(requireContext(), "ALARM ON", Toast.LENGTH_SHORT).show()
        val calendar: Calendar = Calendar.getInstance()

        println("Buraya yazdım.")
        calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker!!.currentHour)
        calendar.set(Calendar.MINUTE, alarmTimePicker!!.currentMinute)

        println(calendar.time.toString())

        time = calendar.getTimeInMillis() - calendar.getTimeInMillis() % 60000
        if (System.currentTimeMillis() > time) {
            time =
                if (Calendar.AM_PM === 0) time + 1000 * 60 * 60 * 12 else time + 1000 * 60 * 60 * 24
        }

        AlarmSettings().setAlarm(requireActivity(),number,time,title.text.toString())



            val alarm = Alarm(
                number,
                time,
                title.text.toString(),
                note.text.toString(),
                true,
                calendar.time.toString())



        DBManager().setAlarm(requireActivity(),alarm)

        clockList.add(Alarm(number,time,title.text.toString(),note.text.toString(),true, calendar.time.toString()))


        println(clockList)



    }




    companion object {


        var clockList = mutableListOf<Alarm>()
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment One.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SetAlarm().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}