package com.example.stagepfe.Patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stagepfe.R

class AccountPatientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_patient)

        var home = PatientAccountFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentPatient, home).commit()
    }
}