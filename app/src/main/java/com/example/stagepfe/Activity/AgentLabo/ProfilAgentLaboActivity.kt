package com.example.stagepfe.Activity.AgentLabo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.stagepfe.Fragments.AgentLabo.ProfilAgentUpdateFragment
import com.example.stagepfe.Fragments.Pharmacien.PharmacienProfilUpdateFragment
import com.example.stagepfe.R

class ProfilAgentLaboActivity : AppCompatActivity() {

    var profilAgentLayout: LinearLayout? = null
    var updateProfilAgent: ImageView? =null
    var updateProfilAgentLayout: LinearLayout? = null
    var nameAgent: TextView? = null
    var phoneNumbreAgent: TextView? = null
    var birthDateAgent: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_agent_labo)
        initView()
    }

    private fun initView() {

            updateProfilAgent=findViewById<ImageView>(R.id.IVprofilAgentUpdate)
            profilAgentLayout=findViewById(R.id.profilAgentLayoutContainer)
            updateProfilAgentLayout=findViewById(R.id.UpdateprofilAgentLayoutContainer)
            nameAgent = findViewById(R.id.name_Agent)
            phoneNumbreAgent= findViewById(R.id.PhoneNumber_Agent)
            birthDateAgent = findViewById(R.id.BirthDate_Agent)


////////////////////////////////////////////////////////////////////////////////////////////////////
            updateProfilAgent!!.setOnClickListener {
                profilAgentLayout!!.visibility = View.GONE
                updateProfilAgentLayout!!.visibility = View.VISIBLE


                var UpadteProfilAgent = ProfilAgentUpdateFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.UpdateprofilPharmacienLayoutContainer, UpadteProfilAgent).commit()


            }
    }
}