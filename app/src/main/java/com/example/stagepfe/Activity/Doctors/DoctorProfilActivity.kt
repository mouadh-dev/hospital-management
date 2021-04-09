package com.example.stagepfe.Activity.Doctors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.stagepfe.Adapters.Patients.ViewPagerAdapter
import com.example.stagepfe.Fragments.Doctor.ProfilDoctorMyPatientFragment
import com.example.stagepfe.Fragments.Doctor.ProfilDoctorMyRdvFragment
import com.example.stagepfe.R
import com.google.android.material.tabs.TabLayout

class DoctorProfilActivity : AppCompatActivity() {

    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profil)
        initView()
    }

    private fun initView() {
        viewPager = findViewById(R.id.View_Pager_Doctor)
        tabs = findViewById(R.id.tabs_ViewPager_Doctor)

        var adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ProfilDoctorMyRdvFragment(), "Mes Rendez-vous ")
        adapter.addFragment(ProfilDoctorMyPatientFragment(), " Mes patients ")

        viewPager!!.adapter = adapter
        tabs!!.setupWithViewPager(viewPager)
    }
}