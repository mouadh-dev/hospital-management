package com.example.stagepfe.Doctor.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stagepfe.Doctor.Fragment.DoctorMessageFragment
import com.example.stagepfe.Doctor.Fragment.DoctorNotificationFragment
import com.example.stagepfe.Doctor.Fragment.DoctorReclamationFragment
import com.example.stagepfe.R

class AccountDoctorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_doctor)
        var home = DoctorMessageFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentDoctor, home).commit()
    }
}