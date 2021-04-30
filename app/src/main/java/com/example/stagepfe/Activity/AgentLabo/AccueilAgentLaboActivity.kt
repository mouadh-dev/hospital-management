package com.example.stagepfe.Activity.AgentLabo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.stagepfe.Activity.Pharmacien.ProfilPharmacienActivity
import com.example.stagepfe.Fragments.AgentLabo.AgentAccueilFragment
import com.example.stagepfe.Fragments.AgentLabo.AgentReclamationFragment
import com.example.stagepfe.Fragments.Doctor.DoctorReclamationFragment
import com.example.stagepfe.Fragments.Pharmacien.AccueilPharmacienFragment
import com.example.stagepfe.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class AccueilAgentLaboActivity : AppCompatActivity() {

    var navigationAgent: BottomNavigationView? = null
    private val pickImage = 100
    private var imageUri: Uri? = null
    var imageProfilAgent: ImageView? = null
    var reclamationAgent: LinearLayout? = null
    var homeAgent: LinearLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil_agent_labo)
        var homeLaboratoire = AgentAccueilFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentAgent, homeLaboratoire).commit()
        initView()
    }

    private fun initView() {
        navigationAgent = findViewById(R.id.bottom_nav_Agent)
        imageProfilAgent = findViewById(R.id.IVimageProfilAgent)
        reclamationAgent = findViewById(R.id.reclamationLayoutAgent)
        homeAgent = findViewById(R.id.profilInformationAgent)

        imageProfilAgent!!.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)

        }
//////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////

        navigationAgent!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home_phar -> {
                    homeAgent!!.visibility = View.VISIBLE
                    reclamationAgent!!.visibility = View.GONE

                    var homeLaboratoire = AgentAccueilFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentAgent, homeLaboratoire).commit()

                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_profile_phar -> {

                    var intent = Intent(this, ProfilAgentLaboActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                R.id.navigation_reclamation_phar -> {
                    reclamationAgent!!.visibility = View.VISIBLE
                    homeAgent!!.visibility = View.GONE


                    var reclamationnav = AgentReclamationFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentAgent, reclamationnav).commit()

                    return@OnNavigationItemSelectedListener true
                }
            }
            true
        })
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageProfilAgent!!.setImageURI(imageUri)
        }
    }
}