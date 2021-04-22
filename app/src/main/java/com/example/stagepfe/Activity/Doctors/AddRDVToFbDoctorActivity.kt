package com.example.stagepfe.Activity.Doctors

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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
    var phonePatient: EditText? = null
    var numberPatient: String? = null
    var cancelButton: Button? = null
    var adapter: ArrayAdapter<String>?=null
    var names: ArrayList<String?>?=null

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
        phonePatient = findViewById(R.id.phone_numbre)
        cancelButton = findViewById(R.id.Cancel_Button_rdv)
        initAdapter()

///////////////////////////////////////////cancelButton////////////////////////////////////////////
        cancelButton!!.setOnClickListener {
            var intent = Intent(this, AccountDoctorActivity::class.java)
            startActivity(intent)
            finish()
        }
////////////////////////////////////////////////////////////////////////////////////////////////////
        time = intent.getStringExtra("hour")!!.toString()
        year = intent.getStringExtra("year")!!.toString()
        day = intent.getStringExtra("day")!!.toString()
        month = intent.getStringExtra("month")!!.toString()
        var m:Int = month!!.toInt()
        m+=1
        month = m.toString()

        var userdao = UserDao()
        userdao.retrieveDataUser(this, UserItem(), object : UserCallback {

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

            userdao.insertappointment(appointment, userItem, FirebaseAuth.getInstance().uid.toString(), object : AppointmentCallback {
                override fun successAppointment(appointment: Appointment) {
                    var toast = Toast.makeText(
                        applicationContext,
                        "Rendez-vous ajoute avec succès",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }

                override fun failureAppointment() {

                }


            })
        }
/////////////////////////////////////////Suggestion Search//////////////////////////////////////////
        populateSearch()


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

    private fun populateSearch() {
        val ref = FirebaseDatabase.getInstance().getReference("users")
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ds in snapshot.children) {
                        var nom = ds.child("nom").getValue(String::class.java)
                        var prenom = ds.child("prenom").getValue(String::class.java)
                        var role = ds.child("role").child("0").getValue(String::class.java)
                        var number = ds.child("phonenumber").getValue(String::class.java)


                        var fullName = "$nom $prenom"

                        numberPatient = "$number"
                        names!!.add(fullName)

//                        phonePatient!!.setText("$numero")
                    }

                    adapter!!.notifyDataSetChanged()
                    namePatient!!.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                        override fun onItemClick(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            var selection: String = parent!!.getItemAtPosition(position).toString()
                            phonePatient!!.setText(numberPatient)
                        }

                    })


                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "signInWithEmail:failure", error.toException())

            }
        }
        ref.addListenerForSingleValueEvent(eventListener)
    }
}