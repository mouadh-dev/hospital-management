package com.example.stagepfe.Activity.Patients

import android.os.Bundle
import android.view.View.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.stagepfe.Fragments.Patient.OrdonancePatientFragment
import com.example.stagepfe.Fragments.Patient.RapportPatientFragment
import com.example.stagepfe.Fragments.Patient.RendezVousPatientFragment
import com.example.stagepfe.R
import com.example.stagepfe.Adapters.Patients.ViewPagerAdapter
import com.example.stagepfe.Fragments.Patient.ModifyProfilePatientFragment
import com.google.android.material.tabs.TabLayout


class ProfilePatientActivity : AppCompatActivity() {
    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null
    var updatePatient: ImageView? = null
    var containerUpdate: FrameLayout? = null
    var containerprofileViwPager: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_patient)
///////////////////////////////////////////////////////////////////////////////////////////////////



///////////////////////////////////////////////////////////////////////////////////////////////////
        initView()
//        var contentProfilPatient = RendezVousPatientFragment()
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.Container_Information_Patient, contentProfilPatient)
//            .commit()
    }

    private fun initView() {

        viewPager = findViewById(R.id.View_Pager)
        tabs = findViewById(R.id.tabs_ViewPager)
        updatePatient = findViewById(R.id.update_Profile_Patient)
        containerUpdate = findViewById(R.id.container_update_profile_patient)
        containerprofileViwPager = findViewById(R.id.container_update_first)

//////////////////////////////////////////////////////////////////////////////////////////////////

        updatePatient!!.setOnClickListener{

            containerprofileViwPager!!.visibility = GONE
            containerUpdate!!.visibility = VISIBLE

            var update = ModifyProfilePatientFragment()
            supportFragmentManager.beginTransaction().replace(R.id.UpdateProfilDoctorContainer, update)
                .commit()
        }


////////////////////////////////////////View Pager//////////////////////////////////////////////////

        var adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(RendezVousPatientFragment(), " Rendez-vous ")
        adapter.addFragment(RapportPatientFragment(), " Rapports ")
        adapter.addFragment(OrdonancePatientFragment(), " Ordonances ")

        viewPager!!.adapter = adapter
        tabs!!.setupWithViewPager(viewPager)


//////////////////////////////////////////////////////////////////////////////////////////////////



    }



    ///////////////////////////////////////////////////////////////////////////////////////////////
}