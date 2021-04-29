package com.example.stagepfe.Activity.Doctors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.stagepfe.Adapters.Patients.ViewPagerAdapter
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Fragments.Doctor.ProfilDoctorMyPatientFragment
import com.example.stagepfe.Fragments.Doctor.ProfilDoctorMyRdvFragment
import com.example.stagepfe.Fragments.Doctor.ShowOrdonnancePatientForDoctorFragment
import com.example.stagepfe.Fragments.Doctor.ShowRapportPatientForDoctorFragment
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem
import com.google.android.material.tabs.TabLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class ShowInfoPatientForDoctorActivity : AppCompatActivity() {
    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null
    var search: ImageView? = null
    var downImg: ImageView? = null
    var slidPanel: SlidingUpPanelLayout? = null
    var namePatient:TextView? = null
    var phoneNumber:TextView? = null
    var datNaiss: TextView? = null
    var userDao = UserDao()
    private var id:String? = null
    private var namePatientFromIntent:String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_info_patient_for_doctor)
        initView()
    }

    private fun initView() {
        viewPager = findViewById(R.id.View_Pager_show_profil_pat)
        tabs = findViewById(R.id.tabs_ViewPager_show_profil_pat)
        search = findViewById(R.id.float_button_ordonnance)
        slidPanel = findViewById(R.id.sliding_layout_ordonnance)
        downImg = findViewById(R.id.DownIC)
        namePatient = findViewById(R.id.name_Patient_doctor)
        phoneNumber = findViewById(R.id.num_phone_patient)
        datNaiss = findViewById(R.id.date_naiss_patient)


        var adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ShowOrdonnancePatientForDoctorFragment(), "Les ordonnances ")
        adapter.addFragment(ShowRapportPatientForDoctorFragment(), " Rapport ")

        viewPager!!.adapter = adapter
        tabs!!.setupWithViewPager(viewPager)

        namePatientFromIntent = intent.getStringExtra("nomPatient")

        userDao.populateSearch(UserItem(), object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                var nameToCompare = userItem.nom + " " + userItem.prenom
                if (nameToCompare.equals(namePatientFromIntent)){
                    id = userItem.id
                    userDao.getUserByUid(id!!, object : UserCallback {
                        override fun onSuccess(userItem: UserItem) {
                            namePatient!!.text = userItem.nom + " " + userItem.prenom
                            phoneNumber!!.text = userItem.phonenumber
                            datNaiss!!.text = userItem.datenaiss

                        }

                        override fun failure() {

                        }
                    })
                }

            }

            override fun failure() {

            }
        })



        namePatient!!.setText(intent.getStringExtra("nom"))


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