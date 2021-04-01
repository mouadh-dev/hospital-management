package com.example.stagepfe.Authentication.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.Authentication.Fragment.ConnexionFragment
import com.example.stagepfe.R

class AuthenticationFragmentContainerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticatio_fragment_container)


        var firstFragment = ConnexionFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentLayout, firstFragment).commit()



    }

}