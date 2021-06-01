package com.example.stagepfe.Activity.Patients

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.stagepfe.Activity.Doctors.CheckRDVActivity
import com.example.stagepfe.Activity.Patients.chat.ChatPtientActivity
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Message
import com.example.stagepfe.entite.UserItem
import com.github.badoualy.datepicker.DatePickerTimeline
import com.github.badoualy.datepicker.MonthView
import java.util.*
import com.github.badoualy.datepicker.MonthView.DateLabelAdapter
import com.github.badoualy.datepicker.MonthView.GONE
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ShowProfilDoctorToPatientActivity : AppCompatActivity() {
    var nameDoctor: TextView? = null
    var specialiteDoctor: TextView? = null
    var bioDoctor: TextView? = null
    var appelerDoctor: TextView? = null
    var contacterDoctor: TextView? = null
    var timeLine: DatePickerTimeline? = null
     var id:String? = null
    var userDao = UserDao()
    private var nameDoctorFromIntent:String? = null
    var doctorNumber: Int?=null
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDateTime: LocalDateTime = LocalDateTime.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_profil_doctor_to_patient)
        initView()
    }

    private fun initView() {
        nameDoctor=findViewById(R.id.showNameDoctorToPatient)
        specialiteDoctor=findViewById(R.id.showSpecialiteDoctorToPatient)
        bioDoctor=findViewById(R.id.BioDoctorToPatient)
        appelerDoctor=findViewById(R.id.AppelerDoctor)
        contacterDoctor=findViewById(R.id.ContacterDoctor)
        timeLine = findViewById(R.id.time_lineDoctorProfil)




        nameDoctorFromIntent = intent.getStringExtra("nameDoctor")
        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                var nameToCompare = userItem.prenom + " " + userItem.nom

                if (nameToCompare.equals(nameDoctorFromIntent)) {
                    id = userItem.id
                    userDao.getUserByUid(id!!, object : UserCallback {
                        override fun onSuccess(userItem: UserItem) {
                            nameDoctor!!.text = userItem.nom + " " + userItem.prenom
                            println("hama" + userItem.nom + " " + userItem.prenom + nameDoctorFromIntent)
                            intent.putExtra("test", userItem.nom + " " + userItem.prenom)
                            specialiteDoctor!!.text = userItem.speciality
                            bioDoctor!!.text = userItem.bio
                            doctorNumber = userItem.phonenumber!!.toInt()

                        }

                        override fun failure() {

                        }
                    })
                }

            }

            override fun failure() {

            }
        })
////////////////////////////////////////////////////////////////////////////////////////////////////
        contacterDoctor!!.setOnClickListener {
            var v = View.inflate(this, R.layout.dialog_ordonance, null)
            var builder = AlertDialog.Builder(this)
            builder.setView(v)
            var dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.findViewById<TextView>(R.id.nameDoctor).visibility = GONE
            dialog.findViewById<ImageView>(R.id.QrCodeIv).visibility = GONE
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
                                        var intent = Intent(
                                            this@ShowProfilDoctorToPatientActivity,
                                            ChatPtientActivity::class.java
                                        )
                                        intent.putExtra("id", id)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this@ShowProfilDoctorToPatientActivity,
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
        appelerDoctor!!.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + doctorNumber)
            startActivity(dialIntent)
        }
////////////////////////////////////////////////////////////////////////////////////////////////////

        timeLine!!.setDateLabelAdapter(DateLabelAdapter { calendar, index ->
            Integer.toString(calendar[Calendar.MONTH] + 1) + "/" + calendar[Calendar.YEAR] % 2000
        })

        timeLine!!.setOnDateSelectedListener { year, month, day, index ->
                var intent = Intent(this, CheckRDVPatientActivity::class.java)
                var months = month+1
                intent.putExtra("key", "Veuillez choisir l'heure du rendez-vous pour $day-$months-$year")
                intent.putExtra("keyday", "$day")
                intent.putExtra("keymonth", "$month")
                intent.putExtra("keyyear", "$year")
                startActivity(intent)
                finish() // If activity no more needed in back stack

        }

    }
}