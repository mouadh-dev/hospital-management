package com.example.stagepfe.Activity.Pharmacien

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Fragments.Doctor.DoctorProfileUpdateFragment
import com.example.stagepfe.Fragments.Pharmacien.PharmacienProfilUpdateFragment
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem

class ProfilPharmacienActivity : AppCompatActivity() {

    var profilPharmacienLayout: LinearLayout? = null
    var updateProfilPharmacien: ImageView? =null
    var updateProfilPharmacienLayout: LinearLayout? = null
    var namePharmacien: TextView? = null
    var phoneNumbrePharmacien: TextView? = null
    var birthDatePharmacien: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_pharmacien)
        initView()
    }

    private fun initView() {
        updateProfilPharmacien=findViewById<ImageView>(R.id.IVprofilPharmacienUpdate)
        profilPharmacienLayout=findViewById(R.id.profilPharmacienLayoutContainer)
        updateProfilPharmacienLayout=findViewById(R.id.UpdateprofilPharmacienLayoutContainer)
        namePharmacien = findViewById(R.id.name_Pharmacien)
        phoneNumbrePharmacien= findViewById(R.id.PhoneNumber_Pharmacien)
        birthDatePharmacien = findViewById(R.id.BirthDate_Pharmacien)

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        var userDao = UserDao()
        userDao.retrieveDataUser(this,
            UserItem(),
            object : UserCallback {
                override fun onSuccess(userItem: UserItem) {
                    namePharmacien!!.text = userItem.nom + " " + userItem.prenom
                    phoneNumbrePharmacien!!.text = userItem.phonenumber
                    birthDatePharmacien!!.text = userItem.datenaiss
                }

                override fun failure() {
                }

            })

////////////////////////////////////////////////////////////////////////////////////////////////////



////////////////////////////////////////////////////////////////////////////////////////////////////
        updateProfilPharmacien!!.setOnClickListener {
            profilPharmacienLayout!!.visibility = View.GONE
            updateProfilPharmacienLayout!!.visibility = View.VISIBLE


            var UpadteProfilPharmacien = PharmacienProfilUpdateFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.UpdateprofilPharmacienLayoutContainer, UpadteProfilPharmacien).commit()


        }
    }
}