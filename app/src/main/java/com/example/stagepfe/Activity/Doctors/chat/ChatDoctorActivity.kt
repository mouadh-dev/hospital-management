package com.example.stagepfe.Activity.Doctors.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Fragments.Doctor.SendMessagesDoctorFragment
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem

class ChatDoctorActivity : AppCompatActivity() {
    private var id: String? = null
    var idPaient: String? = null
    var namePatientchat: TextView? = null
    var userDao = UserDao()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_doctor)
        initView()
    }

    private fun initView() {
        namePatientchat = findViewById(R.id.name_Reciever_Doctor)
        var chat = SendMessagesDoctorFragment()
        supportFragmentManager.beginTransaction().replace(R.id.Container_MessagesChat_Doctor, chat)
            .commit()

        idPaient = intent.getStringExtra("idPatient")

        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {

                if (idPaient.equals(userItem.id)) {
                    namePatientchat!!.text = userItem.nom + " " + userItem.prenom
                    println("hamaya" + userItem.nom + " " + userItem.prenom + idPaient)
                }

            }

            override fun failure() {

            }
        })

    }

    fun getIDReciever(): String {
        return intent.getStringExtra("id")!!
    }
}