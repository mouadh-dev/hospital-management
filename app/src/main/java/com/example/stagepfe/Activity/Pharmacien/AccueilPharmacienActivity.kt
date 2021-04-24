package com.example.stagepfe.Activity.Pharmacien

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import com.example.stagepfe.Activity.Doctors.DoctorProfilActivity
import com.example.stagepfe.Fragments.Doctor.AccueilDoctorFragment
import com.example.stagepfe.Fragments.Doctor.DoctorMessageFragment
import com.example.stagepfe.Fragments.Doctor.DoctorNotificationFragment
import com.example.stagepfe.Fragments.Doctor.DoctorReclamationFragment
import com.example.stagepfe.Fragments.Pharmacien.AccueilPharmacienFragment
import com.example.stagepfe.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class AccueilPharmacienActivity : AppCompatActivity() {

    var navigationPharmacien: BottomNavigationView? = null
    private val pickImage = 100
    private var imageUri: Uri? = null
    var imageProfilPharmacien: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil_pharmacien)
        var homePharmacie = AccueilPharmacienFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentPharmacien, homePharmacie).commit()
        initView()
    }

    private fun initView() {
        navigationPharmacien = findViewById(R.id.bottom_nav_Pharmacien)
        imageProfilPharmacien = findViewById(R.id.IVimageProfilPharmacien)

        imageProfilPharmacien!!.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)

        }
//////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////

        navigationPharmacien!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home_phar -> {

                    var homeParmacien = AccueilPharmacienFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentPharmacien, homeParmacien).commit()

                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_profile_phar -> {

                    var intent = Intent(this, ProfilPharmacienActivity::class.java)
                    startActivity(intent)
                    finish()

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
            imageProfilPharmacien!!.setImageURI(imageUri)
        }
    }
}
