package com.example.stagepfe.Activity.Patients.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.stagepfe.Activity.Patients.ShowProfilDoctorToPatientActivity
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Fragments.Patient.SendMessgesPatientFragment
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem

class ChatPtientActivity : AppCompatActivity() {
    var name:TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_ptient)
        initView()
    }

    private fun initView() {
        name = findViewById(R.id.name_Reciever)
        var chat = SendMessgesPatientFragment()
        supportFragmentManager.beginTransaction().replace(R.id.Container_MessagesChat_Patient, chat)
            .commit()
var userDao = UserDao()
        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                if (userItem.id.equals(intent.getStringExtra("id")!!)){
                    name!!.text = userItem.nom + " " + userItem.prenom
                }
            }

            override fun failure() {
            }
        })

    }
    fun getIDReciever():String{
        return intent.getStringExtra("id")!!
    }
//    fun getIDDoctor():String{
//        return intent.getStringExtra("idDoctor")!!
//    }
}