package com.example.stagepfe.Activity.Doctors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.stagepfe.R

class AddRapportDoctorActivity : AppCompatActivity() {
    var TextRapport: EditText? = null
    var addRapport: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_rapport_doctor)
        initView()
    }

    private fun initView() {
        TextRapport = findViewById(R.id.text_Rapport_Et)
        addRapport= findViewById(R.id.Add_Rapport_Button)
    }

}