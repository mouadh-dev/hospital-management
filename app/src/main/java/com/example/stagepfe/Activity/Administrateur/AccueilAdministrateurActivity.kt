package com.example.stagepfe.Activity.Administrateur

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.example.stagepfe.Activity.Doctors.DoctorProfilActivity
import com.example.stagepfe.Fragments.Administrateur.AccueilAdministrateurFragment
import com.example.stagepfe.Fragments.Administrateur.DemandesAdministrateurBlankFragment
import com.example.stagepfe.Fragments.Administrateur.ReclamationAdministrateurFragment
import com.example.stagepfe.Fragments.Administrateur.UtlisitaeursAdministrateurFragment
import com.example.stagepfe.Fragments.Doctor.AccueilDoctorFragment
import com.example.stagepfe.Fragments.Doctor.DoctorMessageFragment
import com.example.stagepfe.Fragments.Doctor.DoctorNotificationFragment
import com.example.stagepfe.Fragments.Doctor.DoctorReclamationFragment
import com.example.stagepfe.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class AccueilAdministrateurActivity : AppCompatActivity() {

    var navigationAdministrateur: BottomNavigationView? = null
    var reclamationAdministrateur: LinearLayout? = null
    var homeAdministrateur: LinearLayout? = null
    var demandesAdministrateur: LinearLayout? = null
    var utlisateurAdministrateur: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil_administrateur)
        initView()
    }

    private fun initView() {
        navigationAdministrateur=findViewById(R.id.bottom_nav_Administrateur)
        reclamationAdministrateur = findViewById(R.id.reclamationAdministrateurLayout)
        homeAdministrateur = findViewById(R.id.AccueilAdministrateurLayout)
        demandesAdministrateur = findViewById(R.id.DemandesAdministrateurLayout)
        utlisateurAdministrateur = findViewById(R.id.UtlisateurLayout)

        navigationAdministrateur!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_reclamation_adm -> {
                    reclamationAdministrateur!!.visibility = View.VISIBLE
                    homeAdministrateur!!.visibility = View.GONE
                    demandesAdministrateur!!.visibility = View.GONE
                    utlisateurAdministrateur!!.visibility = View.GONE

                    var reclamationAdministrateur = ReclamationAdministrateurFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentAdministrateur, reclamationAdministrateur)
                        .commit()

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_demandes -> {

                    utlisateurAdministrateur!!.visibility = View.GONE
                    reclamationAdministrateur!!.visibility = View.GONE
                    homeAdministrateur!!.visibility = View.GONE
                    demandesAdministrateur!!.visibility = View.VISIBLE


                    var DemandesAdministrateur = DemandesAdministrateurBlankFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentAdministrateur, DemandesAdministrateur).commit()



                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_utilisateurs -> {

                    utlisateurAdministrateur!!.visibility = View.VISIBLE
                    demandesAdministrateur!!.visibility = View.GONE
                    reclamationAdministrateur!!.visibility = View.GONE
                    homeAdministrateur!!.visibility = View.GONE


                    var UtlisatursAdministrateur = UtlisitaeursAdministrateurFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentAdministrateur, UtlisatursAdministrateur).commit()


                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_home_adm -> {
                    homeAdministrateur!!.visibility = View.VISIBLE
                    utlisateurAdministrateur!!.visibility = View.GONE
                    demandesAdministrateur!!.visibility = View.GONE
                    reclamationAdministrateur!!.visibility = View.GONE


                    var HomeAdministrateur = AccueilAdministrateurFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentAdministrateur, HomeAdministrateur)
                        .commit()

                }

            }
            true
        })
        }
}