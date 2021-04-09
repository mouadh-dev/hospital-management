package com.example.stagepfe.Activity.Doctors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.stagepfe.Adapters.Patients.ViewPagerAdapter
import com.example.stagepfe.Fragments.Doctor.ProfilDoctorMyPatientFragment
import com.example.stagepfe.Fragments.Doctor.ProfilDoctorMyRdvFragment
import com.example.stagepfe.Fragments.Doctor.ShowOrdonnancePatientForDoctorFragment
import com.example.stagepfe.Fragments.Doctor.ShowRapportPatientForDoctorFragment
import com.example.stagepfe.R
import com.google.android.material.tabs.TabLayout

class ShowInfoPatientForDoctorActivity : AppCompatActivity() {
    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_info_patient_for_doctor)
        initView()
    }

    private fun initView() {
        viewPager = findViewById(R.id.View_Pager_show_profil_pat)
        tabs = findViewById(R.id.tabs_ViewPager_show_profil_pat)

        var adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ShowOrdonnancePatientForDoctorFragment(), "Les ordonnances ")
        adapter.addFragment(ShowRapportPatientForDoctorFragment(), " Rapport ")

        viewPager!!.adapter = adapter
        tabs!!.setupWithViewPager(viewPager)
    }
}