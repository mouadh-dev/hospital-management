package com.example.stagepfe.Activity.Doctors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.example.stagepfe.Adapters.Patients.ViewPagerAdapter
import com.example.stagepfe.Fragments.Authentication.NewPasswordFragment
import com.example.stagepfe.Fragments.Doctor.DoctorProfileUpdateFragment
import com.example.stagepfe.Fragments.Doctor.ProfilDoctorMyPatientFragment
import com.example.stagepfe.Fragments.Doctor.ProfilDoctorMyRdvFragment
import com.example.stagepfe.Fragments.Patient.PatientAccountFragment
import com.example.stagepfe.Fragments.Patient.PatientReclamationFragment
import com.example.stagepfe.R
import com.google.android.material.tabs.TabLayout

class DoctorProfilActivity : AppCompatActivity() {

    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null
    var updateProfilDoctor: ImageView? =null
    var profilDoctorLayout: LinearLayout? = null
    var updateProfilDoctorLayout: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profil)
        initView()
    }

    private fun initView() {
        viewPager = findViewById(R.id.View_Pager_Doctor)
        tabs = findViewById(R.id.tabs_ViewPager_Doctor)
        updateProfilDoctor=findViewById<ImageView>(R.id.IVprofilDoctorUpdate)
        profilDoctorLayout=findViewById(R.id.profilDoctorLayoutContainer)
        updateProfilDoctorLayout=findViewById(R.id.UpdateprofilDoctorLayoutContainer)


        updateProfilDoctor!!.setOnClickListener {
            profilDoctorLayout!!.visibility = View.GONE
            updateProfilDoctorLayout!!.visibility = View.VISIBLE


           // var UpadteProfilDoctor = DoctorProfileUpdateFragment()
            //supportFragmentManager.beginTransaction()
              //   .replace(R.id.UpdateprofilDoctorLayoutContainer, UpadteProfilDoctor).commit()

            var intent = Intent(this, ShowInfoPatientForDoctorActivity::class.java)
             startActivity(intent)
             finish()

        }
        var adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ProfilDoctorMyRdvFragment(), "Mes Rendez-vous ")
        adapter.addFragment(ProfilDoctorMyPatientFragment(), " Mes patients ")

        viewPager!!.adapter = adapter
        tabs!!.setupWithViewPager(viewPager)
    }
}