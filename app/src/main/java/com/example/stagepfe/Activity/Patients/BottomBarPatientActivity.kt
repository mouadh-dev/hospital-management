package com.example.stagepfe.Activity.Patients

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.Models.Patient.Model
import com.example.stagepfe.Adapters.Patients.MyAdapter
import com.example.stagepfe.Fragments.Patient.MessagePatientFragment
import com.example.stagepfe.Fragments.Patient.NotificationPatintFragment
import com.example.stagepfe.Fragments.Patient.PatientAccountFragment
import com.example.stagepfe.Fragments.Patient.PatientReclamationFragment
import com.example.stagepfe.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class BottomBarPatientActivity : AppCompatActivity() {
    var listview: ListView? = null
    var list = mutableListOf<Model>()
    var search: ImageView? = null
    var slidPanel: SlidingUpPanelLayout? = null
    var downImg: ImageView? = null
    var navigation: BottomNavigationView? = null
    var titlelTV: TextView? = null
    var reclamationLayout: LinearLayout? = null
    var homeLayout: LinearLayout? = null
    var messageLayout: LinearLayout? = null
    var notificatioLayout: LinearLayout? = null
    var txtSearchDoctor: AutoCompleteTextView? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_bar)

        listview = findViewById<ListView>(R.id.list)
        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))

        listview!!.adapter = MyAdapter(this, R.layout.doctors_list, list)

        initView()

        var home = PatientAccountFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentPatient, home)
            .commit()


    }

    private fun initView() {
        search = findViewById(R.id.float_button)
        slidPanel = findViewById(R.id.sliding_layout)
        downImg = findViewById(R.id.DownIC)
        navigation = findViewById(R.id.bottom_nav)
        titlelTV = findViewById(R.id.TitleActivityBottomnavigation)
        reclamationLayout = findViewById(R.id.reclamationLayout)
        homeLayout = findViewById(R.id.profilInformationLayout)
        messageLayout = findViewById(R.id.MessageLayout)
        notificatioLayout = findViewById(R.id.NotificationLayout)
        txtSearchDoctor= findViewById(R.id.TxtSearchDoctor)



        navigation!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_reclamation -> {
                    reclamationLayout!!.visibility = VISIBLE
                    homeLayout!!.visibility = GONE
                    messageLayout!!.visibility = GONE
                    notificatioLayout!!.visibility = GONE

                    var reclamationnav = PatientReclamationFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentPatient, reclamationnav).commit()

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {

                    notificatioLayout!!.visibility = VISIBLE
                    reclamationLayout!!.visibility = GONE
                    homeLayout!!.visibility = GONE
                    messageLayout!!.visibility = GONE

                    var notificationnav = NotificationPatintFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentPatient, notificationnav).commit()



                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_message -> {

                    messageLayout!!.visibility = VISIBLE
                    notificatioLayout!!.visibility = GONE
                    reclamationLayout!!.visibility = GONE
                    homeLayout!!.visibility = GONE

                    var messagenav = MessagePatientFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentPatient, messagenav).commit()


                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_home -> {
                    homeLayout!!.visibility = VISIBLE
                    messageLayout!!.visibility = GONE
                    notificatioLayout!!.visibility = GONE
                    reclamationLayout!!.visibility = GONE

                    var home = PatientAccountFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentPatient, home)
                        .commit()

                }
                R.id.navigation_profile -> {

                    var intent = Intent(this, ProfilePatientActivity::class.java)
                    startActivity(intent)
                    finish()

                }

            }
            true
        })

        search!!.setOnClickListener {
            slidPanel!!.visibility = View.VISIBLE
            search!!.visibility = View.GONE
        }
        downImg!!.setOnClickListener {
            slidPanel!!.visibility = View.GONE
            search!!.visibility = View.VISIBLE
        }
        populateSearch()
    }

    private fun populateSearch() {
        val ref = FirebaseDatabase.getInstance().getReference("users")
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var names: ArrayList<String?> = ArrayList()
                    for (ds in snapshot.children) {
                        var nom = ds.child("nom").getValue(String::class.java)
                        var prenom = ds.child("prenom").getValue(String::class.java)
                        var role = ds.child("role").child("0").getValue(String::class.java)

                        var test = "$nom $prenom"
                        var test2 = "$role"
                        names.add(test)
                        names.add(test2)
//                        names.add(p)
                    }
                    val adapter: ArrayAdapter<*> = ArrayAdapter<String>(
                        this@BottomBarPatientActivity,
                        android.R.layout.simple_list_item_1,
                        names
                    )
                    txtSearchDoctor!!.setAdapter(adapter)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "signInWithEmail:failure", error.toException())
            }
        }
        ref.addListenerForSingleValueEvent(eventListener)
    }
}