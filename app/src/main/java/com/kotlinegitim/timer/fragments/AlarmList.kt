package com.kotlinegitim.timer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.kotlinegitim.timer.database.DBManager
import com.kotlinegitim.timer.R
import com.kotlinegitim.timer.model.Alarm


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AlarmList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var listAlarm : ListView
    var list = mutableListOf<Alarm>()

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


        val view: View = inflater.inflate(R.layout.alarms_fragment_layout, container, false)

        listAlarm = view.findViewById(R.id.alarms)


        return view
    }


    override fun onStart() {

        DBManager().GetandSetAllAlarms(requireActivity(),listAlarm)
        super.onStart()

    }



    companion object {

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
           AlarmList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}