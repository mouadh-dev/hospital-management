package com.example.stagepfe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.Fragment.ChoosePositionFragment
import com.example.stagepfe.Fragment.ConnexionFragment
import com.example.stagepfe.Fragment.FragmentPatientInscription

class AuthenticationFragmentContainerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticatio_fragment_container)


        var firstFragment = FragmentPatientInscription()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentLayout, firstFragment).commit()



    }

}