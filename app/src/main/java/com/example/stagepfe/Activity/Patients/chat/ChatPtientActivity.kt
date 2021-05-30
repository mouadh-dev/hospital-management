package com.example.stagepfe.Activity.Patients.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stagepfe.Activity.Patients.ShowProfilDoctorToPatientActivity
import com.example.stagepfe.Fragments.Patient.SendMessgesPatientFragment
import com.example.stagepfe.R

class ChatPtientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_ptient)
        initView()
    }

    private fun initView() {
        var chat = SendMessgesPatientFragment()
        supportFragmentManager.beginTransaction().replace(R.id.Container_MessagesChat_Patient, chat)
            .commit()


    }
    fun getIDReciever():String{
        return intent.getStringExtra("id")!!
    }
//    fun getIDDoctor():String{
//        return intent.getStringExtra("idDoctor")!!
//    }
}