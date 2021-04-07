package com.example.stagepfe.Patient.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.stagepfe.R

class ReclamationPatientActivity : AppCompatActivity() {
    var backImg: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reclamation_patient)
        initView()
    }

    private fun initView() {
        backImg = findViewById(R.id.IconReturnBack)

        backImg!!.setOnClickListener{
            var intent = Intent(this, AccountPatientActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}