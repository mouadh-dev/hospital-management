package com.example.stagepfe.Patient

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sothree.slidinguppanel.SlidingUpPanelLayout


class AccountPatientActivity : AppCompatActivity() {
    var listview:ListView? = null
    var list = mutableListOf<Model>()
    var search: ImageView? = null
    var slidPanel: SlidingUpPanelLayout? = null
    var downImg: ImageView? = null
    var navigation: BottomNavigationView? =null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_patient)

        listview = findViewById<ListView>(R.id.list)
        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))

        listview!!.adapter = MyAdapter(this, R.layout.doctors_list, list)

initView()





        var home = PatientAccountFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentPatient, home)
            .commit()

    }

    private fun initView() {
        search = findViewById(R.id.float_button)
        slidPanel = findViewById(R.id.sliding_layout)
        downImg = findViewById(R.id.DownIC)
        navigation = findViewById(R.id.bottom_nav)


        navigation!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_reclamation -> {
                    var intent = Intent(this,BottomBarActionPatientActivity::class.java)
                    startActivity(intent)
                    finish()

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {

                    return@OnNavigationItemSelectedListener true
                }

            }
            true
        })

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