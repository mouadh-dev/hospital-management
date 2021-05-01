package com.example.stagepfe.Activity.Patients

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.stagepfe.Activity.Doctors.CheckRDVActivity
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem
import com.github.badoualy.datepicker.DatePickerTimeline
import com.github.badoualy.datepicker.MonthView
import java.util.*
import com.github.badoualy.datepicker.MonthView.DateLabelAdapter

class ShowProfilDoctorToPatientActivity : AppCompatActivity() {
    var nameDoctor: TextView? = null
    var specialiteDoctor: TextView? = null
    var bioDoctor: TextView? = null
    var appelerDoctor: TextView? = null
    var contacterDoctor: TextView? = null
    var timeLine: DatePickerTimeline? = null

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

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        var userDao = UserDao()
        userDao.retrieveCurrentDataUser(
            object : UserCallback {
                override fun onSuccess(userItem: UserItem) {
                    nameDoctor!!.text = userItem.nom + " " + userItem.prenom
                    specialiteDoctor!!.text = userItem.speciality
                    bioDoctor!!.text = userItem.bio
                }

                override fun failure() {
                }

            })

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