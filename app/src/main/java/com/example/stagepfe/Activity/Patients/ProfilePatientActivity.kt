package com.example.stagepfe.Activity.Patients

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.stagepfe.Adapters.Patients.ViewPagerAdapter
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Fragments.Patient.ModifyProfilePatientFragment
import com.example.stagepfe.Fragments.Patient.OrdonancePatientFragment
import com.example.stagepfe.Fragments.Patient.RapportPatientFragment
import com.example.stagepfe.Fragments.Patient.RendezVousPatientFragment
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.lang.StringBuilder


class ProfilePatientActivity : AppCompatActivity() {
    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null
    var updatePatient: ImageView? = null
    var containerUpdate: LinearLayout? = null
    var containerprofileViwPager: LinearLayout? = null
    var namePatient: TextView? = null
    var numbrePatient: TextView? = null
    var birthPAtient: TextView? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_patient)
///////////////////////////////////////////////////////////////////////////////////////////////////
        var user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
//            var userDao = UserDao()
//            userDao.retrieveUSerByid(user.uid)
            var name = user.displayName

        } else {
            // No user is signed in.
        }

///////////////////////////////////////////////////////////////////////////////////////////////////
        initView()

    }

    private fun initView() {

        viewPager = findViewById(R.id.View_Pager)
        tabs = findViewById(R.id.tabs_ViewPager)
        updatePatient = findViewById(R.id.update_Profile_Patient)
        containerUpdate = findViewById(R.id.Container_UpdateAll)
        containerprofileViwPager = findViewById(R.id.Container_ViewPager)
        namePatient = findViewById<TextView>(R.id.Full_Name_Patient)
        numbrePatient = findViewById<TextView>(R.id.Number_Patient)
        birthPAtient = findViewById<TextView>(R.id.Birth_Patient)

//////////////////////////////////////////////////////////////////////////////////////////////////
        var userDao = UserDao()
        userDao.retrieveDataUser(this,
            UserItem(),
            object : UserCallback {
                override fun onSuccess(userItem: UserItem) {
                    namePatient!!.text = userItem.nom + " " + userItem.prenom
                    numbrePatient!!.text = userItem.phonenumber
                    birthPAtient!!.text = userItem.datenaiss
                }

                override fun failure() {
                }

            })


////////////////////////////////////////View Pager//////////////////////////////////////////////////

        var adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(RendezVousPatientFragment(), " Rendez-vous ")
        adapter.addFragment(RapportPatientFragment(), " Rapports ")
        adapter.addFragment(OrdonancePatientFragment(), " Ordonances ")

        viewPager!!.adapter = adapter
        tabs!!.setupWithViewPager(viewPager)


//////////////////////////////////////////////////////////////////////////////////////////////////
        updatePatient!!.setOnClickListener {

            containerUpdate!!.visibility = VISIBLE
            containerprofileViwPager!!.visibility = GONE

            var update = ModifyProfilePatientFragment()
            supportFragmentManager.beginTransaction().replace(R.id.Container_UpdateAll, update)
                .commit()
        }


    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
}