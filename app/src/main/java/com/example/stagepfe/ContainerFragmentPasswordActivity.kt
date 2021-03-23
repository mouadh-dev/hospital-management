package com.example.stagepfe

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.Fragment.ForgotPasswordFragment

class ContainerFragmentPasswordActivity : AppCompatActivity() {
    var BackIcon: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container_fragment_password)
        init()

//        BackIcon!!.setOnClickListener{
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.ContainerForgotPassword, firstTutorialFragment).commit()
//        }


    }

    private fun init() {
        BackIcon = findViewById<ImageView>(R.id.IconReturnBack)
    }
}