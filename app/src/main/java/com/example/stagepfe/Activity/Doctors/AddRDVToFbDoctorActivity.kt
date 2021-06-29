package com.example.stagepfe.Activity.Doctors

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.Dao.AppointmentCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Appointment
import com.example.stagepfe.entite.Notification
import com.example.stagepfe.entite.UserItem
import com.google.firebase.auth.FirebaseAuth

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
    var namePatient: AutoCompleteTextView? = null
    var idPatient: String? = null
//    var idDoctor: String? = null
    var cancelButton: Button? = null
    var adapter: ArrayAdapter<String>?=null
    var names: ArrayList<String?>?=null
    var ids: ArrayList<String?>?=null
    var userdao = UserDao()

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
        namePatient = findViewById<AutoCompleteTextView>(R.id.Name_PatientADD_RDv)
        confirmButton = findViewById<Button>(R.id.btn_confirm_rdv)
        cancelButton = findViewById(R.id.Cancel_Button_rdv)
        initAdapter()

/////////////////////////////////////////Suggestion Search//////////////////////////////////////////


///////////////////////////////////////////cancelButton////////////////////////////////////////////
        cancelButton!!.setOnClickListener {
//            var intent = Intent(this, AccountDoctorActivity::class.java)
//            startActivity(intent)
            finish()
        }
////////////////////////////////////////////////////////////////////////////////////////////////////
        time = intent.getStringExtra("hour")!!.toString()
        year = intent.getStringExtra("year")!!.toString()
        day = intent.getStringExtra("day")!!.toString()
        month = intent.getStringExtra("month")!!.toString()

////////////////////////////////////////////////////////////////////////////////////////////////////
        userdao.retrieveCurrentDataUser( object : UserCallback {

            override fun onSuccess(userItem: UserItem) {
                nameDoctor!!.text = userItem.nom + " " + userItem.prenom
                speciality!!.text = userItem.speciality
                dateRDV!!.text = "$day-$month-$year"
                hourRDV!!.text = time
            }

            override fun failure() {}
        })
///////////////////////////////////////////////////////////////////////////////////////////////////
        confirmButton!!.setOnClickListener {
            userdao.populateSearch(object : UserCallback {
                override fun onSuccess(user: UserItem) {
                    if ((user.nom + " " + user.prenom) == namePatient!!.text.toString().trim()){
                        idPatient = user.id
                    }
                }

                override fun failure() {
                }
            })


            var appointment = Appointment()
            appointment.idDoctor = userdao.getCurrentUserId()
            appointment.date = dateRDV!!.text.toString()
            appointment.idPatient = idPatient
            appointment.dispo = "reserveé"
            appointment.FinishOrNot = "Pas encore"
            appointment.hour = hourRDV!!.text.toString()
            var notification = Notification()
            notification.idPatient = idPatient
            notification.idDoctor = userdao.getCurrentUserId()
            notification.type = "appointment"
            notification.dateNotification = dateRDV!!.text.toString()
            notification.timeNotification = hourRDV!!.text.toString()
///////////////////////////////////////////////////////////////////////////////////////////////////
            userdao.insertappointment(
                appointment,
                notification,
                object : AppointmentCallback {
                    override fun successAppointment(appointment: Appointment) {
                        var toast = Toast.makeText(
                            applicationContext,
                            "Rendez-vous ajoute avec succès",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                        finish()
                    }

                    override fun failureAppointment() {
                    }


                })

        }
/////////////////////////////////////////Suggestion Search//////////////////////////////////////////
        userdao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                var fullName = "${userItem.nom} ${userItem.prenom}"
                names!!.add(fullName)
            }

            override fun failure() {
            }
        })
        adapter!!.notifyDataSetChanged()
    }

    private fun initAdapter() {
         names = ArrayList()
        adapter  = ArrayAdapter<String>(
            this@AddRDVToFbDoctorActivity,
            android.R.layout.simple_list_item_1,
            names as ArrayList<String>
        )
        namePatient!!.setAdapter(adapter)




    }


}