package com.example.stagepfe.Activity.Doctors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.stagepfe.Adapters.Patients.ViewPagerAdapter
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Fragments.Doctor.ProfilDoctorMyPatientFragment
import com.example.stagepfe.Fragments.Doctor.ProfilDoctorMyRdvFragment
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem
import com.google.android.material.tabs.TabLayout

class DoctorProfilActivity : AppCompatActivity() {

    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null
    var updateProfilDoctor: ImageView? =null
    var profilDoctorLayout: LinearLayout? = null
    var updateProfilDoctorLayout: LinearLayout? = null
    var nameDoctor: TextView? = null
    var bioDoctor: TextView? = null
    var phoneNumbreDoctor: TextView? = null
    var birthDateDoctor: TextView? = null
    var bundle =Bundle()

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
        nameDoctor = findViewById(R.id.name_Doctor)
        bioDoctor=findViewById(R.id.BioDoctorProfil)
        phoneNumbreDoctor = findViewById(R.id.PhoneNumber_Doctor)
        birthDateDoctor = findViewById(R.id.BirthDate_Doctor)

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        var userDao = UserDao()
        userDao.retrieveCurrentDataUser(
            object : UserCallback {
                override fun onSuccess(userItem: UserItem) {
                    nameDoctor!!.text = userItem.nom + " " + userItem.prenom
                    phoneNumbreDoctor!!.text = userItem.phonenumber
                    birthDateDoctor!!.text = userItem.datenaiss
                    bioDoctor!!.text = userItem.bio
                }

                override fun failure() {
                }

            })

////////////////////////////////////////////////////////////////////////////////////////////////////


        updateProfilDoctor!!.setOnClickListener {
//            profilDoctorLayout!!.visibility = View.GONE
//           ///////// updateProfilDoctorLayout!!.visibility = View.VISIBLE


//            var UpadteProfilDoctor = DoctorProfileUpdateFragment()
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.UpdateprofilDoctorLayoutContainer, UpadteProfilDoctor).commit()

             var intent = Intent(this, UpdateProfileDoctorActivity::class.java)
              startActivity(intent)
            //  finish()

        }

        var adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ProfilDoctorMyRdvFragment(), "Rendez-vous")
        adapter.addFragment(ProfilDoctorMyPatientFragment(), "patients")

        viewPager!!.offscreenPageLimit = (1)
        viewPager!!.adapter = adapter
        tabs!!.setupWithViewPager(viewPager)
    }
}