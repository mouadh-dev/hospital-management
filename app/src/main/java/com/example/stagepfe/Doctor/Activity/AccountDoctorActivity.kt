package com.example.stagepfe.Doctor.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stagepfe.Doctor.Fragment.DoctorReclamationFragment
import com.example.stagepfe.R

class AccountDoctorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_doctor)
        var home = DoctorReclamationFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFramentDoctor, home).commit()
    }
}