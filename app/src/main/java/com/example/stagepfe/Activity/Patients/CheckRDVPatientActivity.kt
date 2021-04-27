package com.example.stagepfe.Activity.Patients

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stagepfe.Adapters.Patients.MyAdapterAddRDVPatient.OnItemClickListner
import com.example.stagepfe.Adapters.Patients.MyAdapterAddRDVPatient
import com.example.stagepfe.Models.Patient.ModelAddRDVPatient
import com.example.stagepfe.R
import com.github.badoualy.datepicker.DatePickerTimeline
import com.github.badoualy.datepicker.MonthView
import java.util.*
import kotlin.collections.ArrayList

class CheckRDVPatientActivity : AppCompatActivity(), OnItemClickListner {
    var pickerDate: TextView? = null
    var secondTimeLine: DatePickerTimeline? = null
    var returnIcon: ImageView? = null
    var year: Int = 0
    var month: Int = 0
    var day: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_r_d_v_patient)
        initView()
    }
    private fun initView() {
        returnIcon = findViewById(R.id.IconReturnBackMessagePatient)
        pickerDate = findViewById(R.id.pick_date_TV_Patient)
        secondTimeLine = findViewById(R.id.second_time_line_patient)
        pickerDate!!.text = intent.getStringExtra("key")

        ///////////////////////////////////////returnIcon//////////////////////////////////////////////////
        returnIcon!!.setOnClickListener {
            var intent = Intent(this, BottomBarPatientActivity::class.java)
            startActivity(intent)
            finish()
        }
///////////////////////////////////////////////////////////////////////////////////////////////////
        secondTimeLine!!.setDateLabelAdapter(MonthView.DateLabelAdapter { calendar, index ->
            Integer.toString(
                calendar[Calendar.MONTH] + 1
            ) + "/" + calendar[Calendar.YEAR] % 2000

        })
        secondTimeLine!!.setOnDateSelectedListener { year, month, day, index ->
            this.day = day
            this.year = year
            this.month = month + 1



            pickerDate!!.text =
                "Veuillez choisir l'heure du rendez-vous pour" + this.day+"-"+this.month+"-"+this.year
            listOfTime()
        }


    }

    private fun listOfTime() {
        //Recycler View
        val recyclerView: RecyclerView = findViewById(R.id.recycler_viewPatient) as RecyclerView
        //Data
        val dataList: MutableList<ModelAddRDVPatient> = ArrayList()
        dataList.add(ModelAddRDVPatient(R.drawable.itemone,"08:00","vide"))
        dataList.add(ModelAddRDVPatient(R.drawable.itemone,"08:30","vide"))
        dataList.add(ModelAddRDVPatient(R.drawable.itemone,"09:00","vide"))
        dataList.add(ModelAddRDVPatient(R.drawable.itemone,"09:30","vide"))
        dataList.add(ModelAddRDVPatient(R.drawable.itemone,"10:00","vide"))
        dataList.add(ModelAddRDVPatient(R.drawable.itemone,"10:30","vide"))
        dataList.add(ModelAddRDVPatient(R.drawable.itemone,"11:00","vide"))
        dataList.add(ModelAddRDVPatient(R.drawable.itemone,"11:30","vide"))
        dataList.add(ModelAddRDVPatient(R.drawable.itemone,"12:00","vide"))
        dataList.add(ModelAddRDVPatient(R.drawable.itemone,"12:30","vide"))
        dataList.add(ModelAddRDVPatient(R.drawable.itemone,"13:00","vide"))
        dataList.add(ModelAddRDVPatient(R.drawable.itemone,"13:30","vide"))
        dataList.add(ModelAddRDVPatient(R.drawable.itemone,"14:00","vide"))

        //Recycler View Adapter
        val mAdapter = MyAdapterAddRDVPatient(dataList as ArrayList<ModelAddRDVPatient>, this,this)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setLayoutManager(mLayoutManager)
        recyclerView.setItemAnimator(DefaultItemAnimator())
        recyclerView.setAdapter(mAdapter)
    }


    override fun onItemClick(item: ModelAddRDVPatient, position: Int) {

        var hourSended = item.timePat
        var year = year.toString()
        var month = month.toString()
        var day = day.toString()

        var intent = Intent(this, AddRDVToPatientActivity::class.java)
        intent.putExtra("hour", hourSended)
        intent.putExtra("year", year)
        intent.putExtra("month", month)
        intent.putExtra("day", day)

        startActivity(intent)
        finish()
    }
}