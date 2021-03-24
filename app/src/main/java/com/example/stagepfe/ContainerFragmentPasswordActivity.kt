package com.example.stagepfe

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.Fragment.ForgotPasswordFragment
import kotlin.concurrent.fixedRateTimer

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