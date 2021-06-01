package com.example.stagepfe.Activity.Doctors

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.stagepfe.Activity.Patients.chat.ChatPtientActivity
import com.example.stagepfe.Adapters.Patients.ViewPagerAdapter
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Fragments.Doctor.ShowOrdonnancePatientForDoctorFragment
import com.example.stagepfe.Fragments.Doctor.ShowRapportPatientForDoctorFragment
import com.example.stagepfe.R
import com.example.stagepfe.entite.Message
import com.example.stagepfe.entite.UserItem
import com.github.badoualy.datepicker.MonthView
import com.google.android.material.tabs.TabLayout
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ShowInfoPatientForDoctorActivity : AppCompatActivity() {
    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null
    var search: ImageView? = null
    var downImg: ImageView? = null
    var appelerPatient: TextView? = null
    var contacterPatient: TextView? = null
//    var slidPanel: SlidingUpPanelLayout? = null
    var namePatient:TextView? = null
    var datNaiss: TextView? = null
    var userDao = UserDao()
    private var id:String? = null
  var namePatientFromIntent:String? = null
    var patientNumber: Int?=null
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDateTime: LocalDateTime = LocalDateTime.now()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_info_patient_for_doctor)
        initView()
    }

    private fun initView() {
        viewPager = findViewById(R.id.View_Pager_show_profil_pat)
        tabs = findViewById(R.id.tabs_ViewPager_show_profil_pat)
//        search = findViewById(R.id.float_button_ordonnance)
//        slidPanel = findViewById(R.id.sliding_layout_ordonnance)
        downImg = findViewById(R.id.DownIC)
        namePatient = findViewById(R.id.name_Patient_doctor)
        datNaiss = findViewById(R.id.date_naiss_patient)
        appelerPatient=findViewById(R.id.AppelerPatient)
        contacterPatient=findViewById(R.id.ContacterPatient)
////////////////////////////////////////////////////////////////////////////////////////////////////
        contacterPatient!!.setOnClickListener {
            var v = View.inflate(this, R.layout.dialog_ordonance, null)
            var builder = AlertDialog.Builder(this)
            builder.setView(v)
            var dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.findViewById<TextView>(R.id.nameDoctor).visibility = MonthView.GONE
            dialog.findViewById<ImageView>(R.id.QrCodeIv).visibility = MonthView.GONE
            dialog.findViewById<TextView>(R.id.namePatient).visibility = View.GONE
            dialog.findViewById<TextView>(R.id.editText_message).visibility = View.VISIBLE
            dialog.findViewById<Button>(R.id.btn_remove).setOnClickListener {

                var message = Message()
                userDao.retrieveCurrentDataUser(object : UserCallback {
                    override fun onSuccess(userItem: UserItem) {
                        userDao.populateSearch(object : UserCallback {
                            @RequiresApi(Build.VERSION_CODES.O)
                            override fun onSuccess(user: UserItem) {
                                if (id.equals(user.id)) {
                                    var msg =
                                        dialog.findViewById<TextView>(R.id.editText_message).text.toString()
                                    if (!msg.equals("")) {
                                        message.sender = userItem.id
                                        message.reciever = id
                                        message.message = msg
//                                        message.nameSender = userItem.prenom + " " + userItem.nom
//                                        message.nameReciever = user.prenom + " " + user.nom
                                        message.timemsg = currentDateTime.format(DateTimeFormatter.ISO_TIME)
                                        userDao.sendMesage(message)
                                        dialog.dismiss()
                                        var intent = Intent(this@ShowInfoPatientForDoctorActivity,
                                            ChatPtientActivity::class.java
                                        )
                                        intent.putExtra("id", id)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(this@ShowInfoPatientForDoctorActivity,
                                            "vous ne pouvez pas envoyer un message vide",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }

                            override fun failure() {
                            }
                        })

                    }

                    override fun failure() {

                    }
                })




            }
        }
////////////////////////////////////////////////////////////////////////////////////////////////////
        appelerPatient!!.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + patientNumber)
            startActivity(dialIntent)
        }
////////////////////////////////////////////////////////////////////////////////////////////////////


        tabs!!.setupWithViewPager(viewPager)

        namePatientFromIntent = intent.getStringExtra("nomPatient")

        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                var nameToCompare = userItem.nom + " " + userItem.prenom
                if (nameToCompare.equals(namePatientFromIntent)) {
                    id = userItem.id
                    userDao.getUserByUid(id!!, object : UserCallback {
                        override fun onSuccess(userItem: UserItem) {
                            namePatient!!.text = userItem.nom + " " + userItem.prenom
                            intent.putExtra("test", userItem.nom + " " + userItem.prenom)
                            datNaiss!!.text = userItem.datenaiss
                            patientNumber = userItem.phonenumber!!.toInt()
                        }
                        override fun failure() {
                        }
                    })
                }

            }

            override fun failure() {

            }
        })

//        namePatient!!.setText(intent.getStringExtra("nom"))



        var adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ShowOrdonnancePatientForDoctorFragment(), "Ordonnances ")
        adapter.addFragment(ShowRapportPatientForDoctorFragment(), " Rapport ")
        viewPager!!.adapter = adapter
//        search!!.setOnClickListener {
//            slidPanel!!.visibility = View.VISIBLE
//            search!!.visibility = View.GONE
//        }
//        downImg!!.setOnClickListener {
//            slidPanel!!.visibility = View.GONE
//            search!!.visibility = View.VISIBLE
//        }
    }
    fun getMyData(): String? {
        return namePatientFromIntent
    }
}