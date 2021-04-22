package com.example.stagepfe.Activity.Doctors

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stagepfe.Adapters.Doctor.MyAdapterAddRDV
import com.example.stagepfe.Adapters.Doctor.MyAdapterAddRDV.OnItemClickListner
import com.example.stagepfe.Models.Doctors.ModelAddRDV
import com.example.stagepfe.R
import com.github.badoualy.datepicker.DatePickerTimeline
import com.github.badoualy.datepicker.MonthView.DateLabelAdapter

import java.util.*
import kotlin.collections.ArrayList


class CheckRDVActivity : AppCompatActivity(), OnItemClickListner {

    var pickerDate: TextView? = null
    var secondTimeLine: DatePickerTimeline? = null
    var returnIcon:ImageView? = null
    var year: Int = 0
    var month: Int = 0
    var day: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_r_d_v)

        initView()
    }

    private fun initView() {

returnIcon = findViewById(R.id.IconReturnBackMessage)
        pickerDate = findViewById(R.id.pick_date_TV)
        secondTimeLine = findViewById(R.id.second_time_line)
        pickerDate!!.text = intent.getStringExtra("key")
        year = intent.getStringExtra("keyyear")!!.toInt()
        day = intent.getStringExtra("keyday")!!.toInt()
        month = intent.getStringExtra("keymonth")!!.toInt()

///////////////////////////////////////returnIcon//////////////////////////////////////////////////
        returnIcon!!.setOnClickListener {
            var intent = Intent(this, AccountDoctorActivity::class.java)
            startActivity(intent)
            finish()
        }
///////////////////////////////////////////////////////////////////////////////////////////////////
        secondTimeLine!!.setDateLabelAdapter(DateLabelAdapter { calendar, index ->
            Integer.toString(
                calendar[Calendar.MONTH] + 1
            ) + "/" + calendar[Calendar.YEAR] % 2000

        })
        secondTimeLine!!.setOnDateSelectedListener { year, month, day, index ->
            this.day = day
            this.year = year
            this.month = month+1



            pickerDate!!.text =
                "Veuillez choisir l'heure du rendez-vous pour" + this.day+"-"+this.month+"-"+this.year
        }







        //Recycler View
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view) as RecyclerView

        //Data
        val dataList: MutableList<ModelAddRDV> = ArrayList()
        dataList.add(ModelAddRDV(R.drawable.itemone, "08:00", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "08:30", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "09:00", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "09:30", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "10:00", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "10:30", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "11:00", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "11:30", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "12:00", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "12:30", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "13:00", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "13:30", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "14:00", "vide"))

        //Recycler View Adapter
        val mAdapter = MyAdapterAddRDV(dataList as ArrayList<ModelAddRDV>, this, this)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setLayoutManager(mLayoutManager)
        recyclerView.setItemAnimator(DefaultItemAnimator())
        recyclerView.setAdapter(mAdapter)


    }


    override fun onItemClick(item: ModelAddRDV, position: Int) {

        var hourSended = item.time
        var year = year.toString()
        var month = month.toString()
        var day = day.toString()

        var intent = Intent(this, AddRDVToFbDoctorActivity::class.java)
        intent.putExtra("hour", hourSended)
        intent.putExtra("year", year)
        intent.putExtra("month", month)
        intent.putExtra("day", day)

        startActivity(intent)
        finish()
    }


}
