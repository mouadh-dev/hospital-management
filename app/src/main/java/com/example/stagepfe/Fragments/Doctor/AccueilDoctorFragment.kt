package com.example.stagepfe.Fragments.Doctor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import com.example.stagepfe.Activity.Doctors.AccountDoctorActivity
import com.example.stagepfe.Activity.Doctors.CheckRDVActivity
import com.example.stagepfe.Activity.Doctors.DoctorProfilActivity
import com.example.stagepfe.R
import com.github.badoualy.datepicker.DatePickerTimeline
import com.github.badoualy.datepicker.DatePickerTimeline.OnDateSelectedListener
import com.github.badoualy.datepicker.MonthView.DateLabelAdapter
import java.util.*


class AccueilDoctorFragment : Fragment() {
    var timeLine: DatePickerTimeline? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_accueil_doctor, container, false)


        initView(view)
        return view
    }

    private fun initView(view: View) {
        timeLine = view.findViewById(R.id.time_line)

//        timeLine!!.setFirstVisibleDate(2016, Calendar.JULY, 19)
//        timeLine!!.setLastVisibleDate(2020, Calendar.JULY, 19)
        timeLine!!.setDateLabelAdapter(DateLabelAdapter { calendar, index ->
            Integer.toString(calendar[Calendar.MONTH] + 1) + "/" + calendar[Calendar.YEAR] % 2000
        })
        timeLine!!.setOnDateSelectedListener(OnDateSelectedListener { year, month, day, index ->

        })

        timeLine!!.setOnDateSelectedListener { year, month, day, index ->
            requireActivity().run {
                startActivity(Intent(this, CheckRDVActivity::class.java))
                finish() // If activity no more needed in back stack
            }
        }

    }


}

