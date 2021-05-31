package com.example.stagepfe.Activity.Patients

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.stagepfe.Dao.AppointmentCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Appointment
import com.example.stagepfe.entite.UserItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddRDVToPatientActivity : AppCompatActivity() {
    var year: String? = null
    var month: String? = null
    var day: String? = null
    var time: String? = null
    var namePatient: TextView? = null
    var idPatient:String? = null
    var idDoctor:String? = null
    var dateRDV: TextView? = null
    var hourRDV: TextView? = null
    var confirmButton: TextView? = null
    var nameDoctor: AutoCompleteTextView? = null
    var numberDoctor: String? = null
    var cancelButton: Button? = null
    var adapter: ArrayAdapter<String>?=null
    var names: ArrayList<String?>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_r_d_v_to_patient)
        initView()
    }

    private fun initView() {
        namePatient = findViewById<TextView>(R.id.name_Patient_Time)
        dateRDV = findViewById<TextView>(R.id.daterdvPatient)
        hourRDV = findViewById<TextView>(R.id.timerdvPatient)
        nameDoctor = findViewById<AutoCompleteTextView>(R.id.Name_DoctorADD_RDv)
        confirmButton = findViewById<Button>(R.id.btn_confirm_rdvPatient)
        cancelButton = findViewById(R.id.Cancel_Button_rdvPatient)

        initAdapter()
        ///////////////////////////////////////////cancelButton////////////////////////////////////////////
        cancelButton!!.setOnClickListener {
            var intent = Intent(this, BottomBarPatientActivity::class.java)
            startActivity(intent)
            finish()
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        time = intent.getStringExtra("hour")!!.toString()
        year = intent.getStringExtra("year")!!.toString()
        day = intent.getStringExtra("day")!!.toString()
        month = intent.getStringExtra("month")!!.toString()


        var userdao = UserDao()
        userdao.retrieveCurrentDataUser( object : UserCallback {

            override fun onSuccess(userItem: UserItem) {
                namePatient!!.text = userItem.nom + " " + userItem.prenom
                dateRDV!!.text = "$day-$month-$year"
                hourRDV!!.text = time
                idPatient = userItem.id
            }

            override fun failure() {}
        })

        ///////////////////////////////////////////confirmButton////////////////////////////////////////////
        confirmButton!!.setOnClickListener {
            var appointment = Appointment()
            appointment.date = dateRDV!!.text.toString()
            appointment.dispo = "reserveé"
            appointment.FinishOrNot = "Pas encore"
            appointment.hour = hourRDV!!.text.toString()
            appointment.idPatient = idPatient
            userdao.populateSearch(object : UserCallback {
                override fun onSuccess(user: UserItem) {
                    if ((user.nom + " " + user.prenom).equals(nameDoctor!!.text.toString().trim())){
                        appointment.idDoctor = user.id
                        userdao.retrieveCurrentDataUser(object : UserCallback {
                            override fun onSuccess(userItem: UserItem) {
                                userdao.insertappointment(appointment, userItem.id!!, object :
                                    AppointmentCallback {
                                    override fun successAppointment(appointment: Appointment) {
                                        var toast = Toast.makeText(
                                            applicationContext,
                                            "Rendez-vous ajoute avec succès",
                                            Toast.LENGTH_SHORT)
                                        toast.show()
                                        finish()
                                    }

                                    override fun failureAppointment() {
                                        var toast = Toast.makeText(
                                            applicationContext,
                                            "il y a une probleme",
                                            Toast.LENGTH_SHORT)
                                        toast.show()
                                    }


                                })
                            }

                            override fun failure() {
                            }
                        })
                    }
                }

                override fun failure() {
                }
            })


        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////Suggestion Search//////////////////////////////////////////
        populateSearch()


    }

    private fun initAdapter() {
        names = ArrayList()
        adapter  = ArrayAdapter<String>(
            this@AddRDVToPatientActivity,
            android.R.layout.simple_list_item_1,
            names as ArrayList<String>
        )
        nameDoctor!!.setAdapter(adapter)


    }

    private fun populateSearch() {
        val ref = FirebaseDatabase.getInstance().getReference("users")
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ds in snapshot.children) {
                        var nom = ds.child("nom").getValue(String::class.java)
                        var prenom = ds.child("prenom").getValue(String::class.java)
                        var number = ds.child("phonenumber").getValue(String::class.java)


                        var fullName = "$nom $prenom"

                        numberDoctor = "$number"
                        names!!.add(fullName)

//                        phonePatient!!.setText("$numero")
                    }

                    adapter!!.notifyDataSetChanged()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "signInWithEmail:failure", error.toException())

            }
        }
        ref.addListenerForSingleValueEvent(eventListener)
    }

}
