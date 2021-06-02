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
    private var id: String? = null
    var idDoctor: String? = null
    var nameDoctorchat: TextView? = null
    var userDao = UserDao()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_ptient)
        initView()
    }

    private fun initView() {
        nameDoctorchat = findViewById(R.id.name_Reciever)
        var chat = SendMessgesPatientFragment()
        supportFragmentManager.beginTransaction().replace(R.id.Container_MessagesChat_Patient, chat)
            .commit()

        idDoctor = intent.getStringExtra("idPatient")

        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {

                if (idDoctor.equals(userItem.id)) {
                    nameDoctorchat!!.text = userItem.prenom + " " + userItem.nom
                    println("hamaya" + userItem.nom + " " + userItem.prenom + idDoctor)
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