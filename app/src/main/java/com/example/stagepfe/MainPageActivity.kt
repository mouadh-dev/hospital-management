package com.example.stagepfe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stagepfe.Activity.Authentication.AuthenticationFragmentContainerActivity
import java.util.*

class MainPageActivity : AppCompatActivity() {

    lateinit var timer: Timer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        timer = Timer()
        timer.schedule(object:TimerTask() {
            lateinit var kotlin:Unit
            init{

            }

            override fun run() {
                val intent = Intent(this@MainPageActivity, AuthenticationFragmentContainerActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 5000)
    }
}