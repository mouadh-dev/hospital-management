package com.example.stagepfe.Activity.Authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.Fragments.Authentication.ConnexionFragment
import com.example.stagepfe.Activity.Patients.BottomBarActivity
import com.example.stagepfe.R
import com.google.firebase.auth.FirebaseAuth

class AuthenticationFragmentContainerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticatio_fragment_container)


        var firstFragment = ConnexionFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentLayout, firstFragment).commit()



    }

    //override fun onResume() {
       // super.onResume()
      //  if(FirebaseAuth.getInstance().currentUser!=null){
      //      startActivity(Intent(this, BottomBarActivity::class.java))
       // }
    //}

}