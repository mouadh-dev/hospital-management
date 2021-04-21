package com.example.stagepfe.Activity.Doctors

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Appointment
import com.example.stagepfe.entite.UserItem
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




        time = intent.getStringExtra("hour")!!.toString()
        year = intent.getStringExtra("year")!!.toString()
        day = intent.getStringExtra("day")!!.toString()
        month = intent.getStringExtra("month")!!.toString()

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

            userdao.insertappointment(appointment, userItem, userItem.id!!, object : UserCallback {
                override fun onSuccess(userItem: UserItem) {
                    var toast = Toast.makeText(applicationContext, "Rendez-vous ajoute avec succès", Toast.LENGTH_SHORT)
                    toast.show()
                }

                override fun failure() {}

            })
        }
/////////////////////////////////////////Suggestion Search//////////////////////////////////////////
populateSearch()

//            listviewPatientNameSearch!!.visibility = View.VISIBLE
//            userdao.populateSearch(object: SearchCalback{
//                override fun success(n:String) {
//
//                    listPatientSearch.add(ModelSearchRdv(n))
//                    listviewPatientNameSearch!!.adapter = MyAdapterSearchRdv(this@AddRDVToFbDoctorActivity, R.layout.row_search_rdv, listPatientSearch)
//                }
//
//                override fun failure() {
//                }
//
//            })


    }

    private fun populateSearch() {
         val ref = FirebaseDatabase.getInstance().getReference("users")
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var names: ArrayList<String?> = ArrayList()
                    for (ds in snapshot.children) {
                        var nom= ds.child("nom").getValue(String::class.java)
                        var prenom= ds.child("prenom").getValue(String::class.java)
                        var role= ds.child("role").child("0").getValue(String::class.java)

                        var test = "$nom $prenom"
                        var test2 = "$role"
                        names.add(test)
                        names.add(test2)
//                        names.add(p)
                    }
                    val adapter: ArrayAdapter<*> = ArrayAdapter<String>(this@AddRDVToFbDoctorActivity,android.R.layout.simple_list_item_1, names)
                    namePatient!!.setAdapter(adapter)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "signInWithEmail:failure", error.toException())

            }
        }
        ref.addListenerForSingleValueEvent(eventListener)
    }
}