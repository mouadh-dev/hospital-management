package com.example.stagepfe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.Fragment.ConnexionFragment

class AuthenticationFragmentContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticatio_fragment_container)


        var firstFragment = ConnexionFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentLayout, firstFragment).commit()



    }

}