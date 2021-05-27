package com.example.stagepfe.Activity.Patients.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stagepfe.Fragments.Patient.MessgesPatientFragment
import com.example.stagepfe.Fragments.Patient.PatientAccountFragment
import com.example.stagepfe.R

class ChatPtientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_ptient)
        initView()
    }

    private fun initView() {
        var chat = MessgesPatientFragment()
        supportFragmentManager.beginTransaction().replace(R.id.Container_MessagesChat_Patient, chat)
            .commit()
    }
}