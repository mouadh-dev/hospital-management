package com.example.stagepfe.Patient

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stagepfe.Authentication.entite.UserItem
import com.example.stagepfe.R
import com.sothree.slidinguppanel.SlidingUpPanelLayout


class AccountPatientActivity : AppCompatActivity() {
    var listview:ListView? = null
    var list = mutableListOf<Model>()
    var search: ImageView? = null
    var slidPanel: SlidingUpPanelLayout? = null
    var downImg: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_patient)

        listview = findViewById<ListView>(R.id.list)
        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))

        listview!!.adapter = MyAdapter(this, R.layout.doctors_list, list)

initView()





        var home = PatientAccountFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentPatient, home)
            .commit()

    }

    private fun initView() {
        search = findViewById(R.id.Search_ic)
        slidPanel = findViewById(R.id.sliding_layout)
        downImg = findViewById(R.id.DownIC)


        search!!.setOnClickListener{
            slidPanel!!.visibility = View.VISIBLE
            search!!.visibility = View.GONE
        }
        downImg!!.setOnClickListener {
            slidPanel!!.visibility = View.GONE
            search!!.visibility = View.VISIBLE
        }
    }

}