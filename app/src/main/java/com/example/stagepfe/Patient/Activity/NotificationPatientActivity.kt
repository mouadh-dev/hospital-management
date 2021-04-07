package com.example.stagepfe.Patient.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.stagepfe.R

class NotificationPatientActivity : AppCompatActivity() {
    var backIconNotification: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        initView()
    }

    private fun initView() {
        backIconNotification = findViewById(R.id.ReturnBackNotificationPatient)

        backIconNotification!!.setOnClickListener{
            var intent = Intent(this, BottomBarActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}