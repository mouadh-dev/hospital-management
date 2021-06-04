package com.example.stagepfe.Activity.Patients

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.stagepfe.Adapters.Patients.ViewPagerAdapter
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Fragments.Patient.OrdonancePatientFragment
import com.example.stagepfe.Fragments.Patient.RapportPatientFragment
import com.example.stagepfe.Fragments.Patient.RendezVousPatientFragment
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth


class ProfilePatientActivity : AppCompatActivity() {
    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null
    var updatePatient: ImageView? = null
    var imageProfilPatient: ImageView? = null
    var containerprofileViwPager: LinearLayout? = null
    var namePatient: TextView? = null
    var numbrePatient: TextView? = null
    var birthPAtient: TextView? = null
    private val pickImage = 100
    private var imageUri: Uri? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_patient)
///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
        initView()

    }

    private fun initView() {

        viewPager = findViewById(R.id.View_Pager_Patient)
        tabs = findViewById(R.id.tabs_ViewPager)
        updatePatient = findViewById(R.id.update_Profile_Patient)
        imageProfilPatient = findViewById(R.id.IVimageProfilPatient)
        containerprofileViwPager = findViewById(R.id.Container_ViewPager)
        namePatient = findViewById<TextView>(R.id.Full_Name_Patient)
        numbrePatient = findViewById<TextView>(R.id.Number_Patient)
        birthPAtient = findViewById<TextView>(R.id.Birth_Patient)


//////////////////////////////////////////////////////////////////////////////////////////////////
        var userDao = UserDao()
        userDao.retrieveCurrentDataUser(
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

            var intent = Intent(this, UpdateProfilePatientActivity::class.java)
            startActivity(intent)
        }


    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
}