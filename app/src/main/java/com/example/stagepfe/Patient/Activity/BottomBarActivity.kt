package com.example.stagepfe.Patient.Activity

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.Authentication.Fragment.ConnexionFragment
import com.example.stagepfe.Patient.Model
import com.example.stagepfe.Patient.MyAdapter
import com.example.stagepfe.Patient.fragment.PatientAccountFragment
import com.example.stagepfe.Patient.fragment.PatientReclamationFragment
import com.example.stagepfe.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class BottomBarActivity : AppCompatActivity() {
    var listview: ListView? = null
    var list = mutableListOf<Model>()
    var search: ImageView? = null
    var slidPanel: SlidingUpPanelLayout? = null
    var downImg: ImageView? = null
    var navigation: BottomNavigationView? = null
    var titlelTV: TextView? = null
    var reclamationLayout: LinearLayout? = null
    var homeLayout: LinearLayout? = null
    var messageLayout: LinearLayout? = null
    var notificatioLayout: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_bar)

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
        titlelTV = findViewById(R.id.TitleActivityBottomnavigation)
        reclamationLayout = findViewById(R.id.reclamationLayout)
        homeLayout = findViewById(R.id.profilInformationLayout)
        messageLayout = findViewById(R.id.MessageLayout)
        notificatioLayout = findViewById(R.id.NotificationLayout)



        navigation!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_reclamation -> {
                    reclamationLayout!!.visibility = VISIBLE
                    homeLayout!!.visibility = GONE
                    messageLayout!!.visibility = GONE
                    notificatioLayout!!.visibility = GONE

                    var reclamationnav = PatientReclamationFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentPatient, reclamationnav).commit()

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {

                    notificatioLayout!!.visibility = VISIBLE
                    reclamationLayout!!.visibility = GONE
                    homeLayout!!.visibility = GONE
                    messageLayout!!.visibility = GONE

                    var notificationnav = PatientReclamationFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentPatient, notificationnav).commit()



                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_message -> {

                    messageLayout!!.visibility = VISIBLE
                    notificatioLayout!!.visibility = GONE
                    reclamationLayout!!.visibility = GONE
                    homeLayout!!.visibility = GONE

                    var messagenav = PatientReclamationFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentPatient, messagenav).commit()


                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_home -> {
                    homeLayout!!.visibility = VISIBLE
                    messageLayout!!.visibility = GONE
                    notificatioLayout!!.visibility = GONE
                    reclamationLayout!!.visibility = GONE

                    var home = PatientAccountFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentPatient, home)
                        .commit()

                }

            }
            true
        })

        search!!.setOnClickListener {
            slidPanel!!.visibility = View.VISIBLE
            search!!.visibility = View.GONE
        }
        downImg!!.setOnClickListener {
            slidPanel!!.visibility = View.GONE
            search!!.visibility = View.VISIBLE
        }

    }
}