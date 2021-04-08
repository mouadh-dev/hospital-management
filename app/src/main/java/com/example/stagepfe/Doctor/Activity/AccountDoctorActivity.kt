package com.example.stagepfe.Doctor.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.example.stagepfe.Doctor.Fragment.AccueilDoctorFragment
import com.example.stagepfe.Doctor.Fragment.DoctorMessageFragment
import com.example.stagepfe.Doctor.Fragment.DoctorNotificationFragment
import com.example.stagepfe.Doctor.Fragment.DoctorReclamationFragment
import com.example.stagepfe.Patient.Model
import com.example.stagepfe.Patient.fragment.MessagePatientFragment
import com.example.stagepfe.Patient.fragment.PatientAccountFragment
import com.example.stagepfe.Patient.fragment.PatientReclamationFragment
import com.example.stagepfe.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class AccountDoctorActivity : AppCompatActivity() {
    var navigationDoctor: BottomNavigationView? = null
    var titlelTV: TextView? = null
    var reclamationDoctor: LinearLayout? = null
    var homeDoctor: LinearLayout? = null
    var messageDoctor: LinearLayout? = null
    var notificationDoctor: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_doctor)
        var home = AccueilDoctorFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentDoctor, home).commit()
        initView()
    }

    private fun initView() {
        navigationDoctor = findViewById(R.id.bottom_nav_Doctor)
        titlelTV = findViewById(R.id.TitleActivityBottomnavigation)
        reclamationDoctor = findViewById(R.id.reclamationLayoutDoctor)
        homeDoctor = findViewById(R.id.profilInformationDoctor)
        messageDoctor = findViewById(R.id.MessageLayoutDoctor)
        notificationDoctor = findViewById(R.id.NotificationLayoutDoctor)

        navigationDoctor!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_reclamation -> {
                    reclamationDoctor!!.visibility = View.VISIBLE
                    homeDoctor!!.visibility = View.GONE
                    messageDoctor!!.visibility = View.GONE
                    notificationDoctor!!.visibility = View.GONE

                    var reclamationnav = DoctorReclamationFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentDoctor, reclamationnav).commit()

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {

                    notificationDoctor!!.visibility = View.VISIBLE
                    reclamationDoctor!!.visibility = View.GONE
                    homeDoctor!!.visibility = View.GONE
                    messageDoctor!!.visibility = View.GONE

                    var notificationnav = DoctorNotificationFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentDoctor, notificationnav).commit()



                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_message -> {

                    messageDoctor!!.visibility = View.VISIBLE
                    notificationDoctor!!.visibility = View.GONE
                    reclamationDoctor!!.visibility = View.GONE
                    homeDoctor!!.visibility = View.GONE

                    var messagenav = DoctorMessageFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentDoctor, messagenav).commit()


                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_home -> {
                    homeDoctor!!.visibility = View.VISIBLE
                    messageDoctor!!.visibility = View.GONE
                    notificationDoctor!!.visibility = View.GONE
                    reclamationDoctor!!.visibility = View.GONE

                    var home = AccueilDoctorFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentDoctor, home)
                        .commit()

                }

            }
            true
        })

    }
}