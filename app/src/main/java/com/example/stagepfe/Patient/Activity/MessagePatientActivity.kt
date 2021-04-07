package com.example.stagepfe.Patient.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.stagepfe.R

class MessagePatientActivity : AppCompatActivity() {
    var backIconMessage: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_patient)
        initView()
    }

    private fun initView() {
        backIconMessage = findViewById(R.id.IconReturnBackMessage)

        backIconMessage!!.setOnClickListener {
            var intent = Intent(this, BottomBarActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}