package com.example.stagepfe.Activity.Doctors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.stagepfe.Adapters.Doctor.MyAdapterAddRDV
import com.example.stagepfe.Models.Doctors.ModelAddRDV
import com.example.stagepfe.Models.Doctors.ModelRdvDocteur
import com.example.stagepfe.R


class CheckRDVActivity : AppCompatActivity() {
    var listviewDoctorAddRdv: ListView? = null
    var listDoctorAddRdv = mutableListOf<ModelAddRDV>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_r_d_v)

        initView()
    }

    private fun initView() {
       listviewDoctorAddRdv = findViewById<ListView>(R.id.list_add_RDV)
        listDoctorAddRdv.add(ModelAddRDV("08:00-08:30","Reserveé"))
        listDoctorAddRdv.add(ModelAddRDV("08:30-09:00","Reserveé"))
        listDoctorAddRdv.add(ModelAddRDV("09:00-09:30","Reserveé"))
        listDoctorAddRdv.add(ModelAddRDV("09:30-10:00","Reserveé"))
        listDoctorAddRdv.add(ModelAddRDV("10:00-10:30","Reserveé"))
        listDoctorAddRdv.add(ModelAddRDV("10:30-11:00","Reserveé"))
        listDoctorAddRdv.add(ModelAddRDV("11:00-11:30","Reserveé"))
        listDoctorAddRdv.add(ModelAddRDV("11:30-12:00","Reserveé"))
        listDoctorAddRdv.add(ModelAddRDV("12:00-12:30","Reserveé"))
        listDoctorAddRdv.add(ModelAddRDV("12:30-13:00","Reserveé"))
        listDoctorAddRdv.add(ModelAddRDV("13:00-13:30","Reserveé"))
        listDoctorAddRdv.add(ModelAddRDV("13:30-14:00","Reserveé"))

        listviewDoctorAddRdv!!.adapter = MyAdapterAddRDV(this,R.layout.list_add_rdv_doctor,listDoctorAddRdv)

    }
}