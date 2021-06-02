package com.example.stagepfe.Activity.Doctors.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Fragments.Doctor.SendMessagesDoctorFragment
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem

class ChatDoctorActivity : AppCompatActivity() {
    var photoDoctor: ImageView? = null
    var namePatientchat: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_doctor)
        initView()
    }

    private fun initView() {
        namePatientchat = findViewById(R.id.name_Reciever_Doctor)
        photoDoctor = findViewById(R.id.roundCardViewChatDoctor)

        var chat = SendMessagesDoctorFragment()
        supportFragmentManager.beginTransaction().replace(R.id.Container_MessagesChat_Doctor, chat)
            .commit()

        var userDao = UserDao()
        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                if (userItem.id.equals(intent.getStringExtra("id")!!)) {
                    namePatientchat!!.text = userItem.nom + " " + userItem.prenom
                    Glide.with(this@ChatDoctorActivity).load(userItem.profilPhotos.toString())
                        .into(photoDoctor!!)
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