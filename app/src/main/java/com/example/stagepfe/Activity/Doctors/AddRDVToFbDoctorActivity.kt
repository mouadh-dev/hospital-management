package com.example.stagepfe.Activity.Doctors

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Appointment
import com.example.stagepfe.entite.UserItem

class AddRDVToFbDoctorActivity : AppCompatActivity() {
    var year: String? = null
    var month: String? = null
    var day: String? = null
    var time: String? = null
    var speciality: TextView? = null
    var nameDoctor: TextView? = null
    var dateRDV: TextView? = null
    var hourRDV: TextView? = null
    var confirmButton: TextView? = null
    var namePatient: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_rdv_to_fb_doctor)
        initView()
    }

    private fun initView() {
        nameDoctor = findViewById<TextView>(R.id.name_Doctor_Time)
        speciality = findViewById<TextView>(R.id.speciality_Doctor)
        dateRDV = findViewById<TextView>(R.id.daterdv)
        hourRDV = findViewById<TextView>(R.id.timerdv)
        namePatient = findViewById<EditText>(R.id.Name_PatientADD_RDv)
        confirmButton = findViewById<Button>(R.id.btn_confirm_rdv)



        time = intent.getStringExtra("hour")!!.toString()
        year = intent.getStringExtra("year")!!.toString()
        day = intent.getStringExtra("day")!!.toString()
        month = intent.getStringExtra("month")!!.toString()
//        intent.getStringExtra("key")

        var userdao = UserDao()
        userdao.retrieveDataUser(this,
            UserItem(),
            object : UserCallback {

                override fun onSuccess(userItem: UserItem) {
                    nameDoctor!!.text = userItem.nom + " " + userItem.prenom
                    speciality!!.text = userItem.speciality
                    dateRDV!!.text = "$day-$month-$year"
                    hourRDV!!.text = time
                }

                override fun failure() {}
            })

        confirmButton!!.setOnClickListener {

            var appointment = Appointment()
            var userItem = UserItem()
            appointment.nameDoctor = nameDoctor!!.text.toString()
            appointment.date = dateRDV!!.text.toString()
            appointment.namePatient = namePatient!!.text.toString().trim()
            appointment.dispo = "reserveé"
            appointment.FinishOrNot = "Pas encore"
            appointment.hour = hourRDV!!.text.toString()

            userdao.insertappointment(appointment, userItem, userItem.id!!, object : UserCallback {
                override fun onSuccess(userItem: UserItem) {
//                    var takenAppointment: TextView = findViewById(R.id.tvTextTaken)
//                    takenAppointment.text = namePatient!!.text.toString().trim()
                    var toast = Toast.makeText(applicationContext, "Rendez-vous ajoute avec succès", Toast.LENGTH_SHORT)
                    toast.show()
                }

                override fun failure() {}

            })
        }
    }
}