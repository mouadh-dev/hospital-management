package com.example.stagepfe.Activity.Authentication

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.Fragments.Authentication.ForgotPasswordFragment
import com.example.stagepfe.R

class ContainerFragmentPasswordActivity : AppCompatActivity() {
    var BackIcon: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container_fragment_password)
        init()

        var Password = ForgotPasswordFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerForgotPassword,Password).commit()

        }





    private fun init() {

    }
}