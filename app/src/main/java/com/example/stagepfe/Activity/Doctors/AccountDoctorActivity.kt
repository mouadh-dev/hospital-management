package com.example.stagepfe.Activity.Doctors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.stagepfe.Fragments.Doctor.AccueilDoctorFragment
import com.example.stagepfe.Fragments.Doctor.DoctorMessageFragment
import com.example.stagepfe.Fragments.Doctor.DoctorNotificationFragment
import com.example.stagepfe.Fragments.Doctor.DoctorReclamationFragment
import com.example.stagepfe.Models.Doctors.ModelPatientList
import com.example.stagepfe.Adapters.Doctor.MyAdapterListPatientForDoctors
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Appointment
import com.example.stagepfe.entite.UserItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class AccountDoctorActivity : AppCompatActivity() {

    var listviewPatientForDoctor: ListView? = null
    var listPatientForDoctor = mutableListOf<ModelPatientList>()
    var search: ImageView? = null
    var slidPanel: SlidingUpPanelLayout? = null
    var downImg: ImageView? = null
    var navigationDoctor: BottomNavigationView? = null
    var titlelTV: TextView? = null
    var reclamationDoctor: LinearLayout? = null
    var homeDoctor: LinearLayout? = null
    var messageDoctor: LinearLayout? = null
    var notificationDoctor: LinearLayout? = null
    var txtSearch: AutoCompleteTextView? =null
    lateinit var ref : DatabaseReference
//    var reglage: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_doctor)
        ref=FirebaseDatabase.getInstance().getReference("users")



        var home = AccueilDoctorFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentDoctor, home).commit()
        initView()
        populateSearch()
    }
//////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    private fun populateSearch() {
       ref.addValueEventListener(object :ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot!!.exists()){
                listPatientForDoctor.clear()
                ArrayList<String> patient=new ArrayList<>()
                for (e in snapshot.children){
                    val Users = e.getValue(users::class.java)
                    listPatientForDoctor.add(Users!! as ModelPatientList )
                    txtSearch!!.setAdapter(MyAdapterListPatientForDoctors)
                }
                ArrayAdapter adapter=new ArrayAdapter(applicationContext(),android.R.layout.list_patient_for_doctor)
            }
           }

           override fun onCancelled(error: DatabaseError) {
           }

       })
ref.addListenerForSingleValueEvent(eventListener)

    }
////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private fun initView() {
        search = findViewById(R.id.float_button)
        slidPanel = findViewById(R.id.sliding_layout)
        downImg = findViewById(R.id.DownIC)

//        reglage = findViewById(R.id.reglage_ic)
//        reglage!!.setOnClickListener {
//           var userDao = UserDao()
//            var appointment: Appointment = Appointment()
//            appointment.date = "test"
//            userDao.insertappointment(appointment)
//        }

        navigationDoctor = findViewById(R.id.bottom_nav_Doctor)
        titlelTV = findViewById(R.id.TitleActivityBottomnavigation)
        reclamationDoctor = findViewById(R.id.reclamationLayoutDoctor)
        homeDoctor = findViewById(R.id.profilInformationDoctor)
        messageDoctor = findViewById(R.id.MessageLayoutDoctor)
        notificationDoctor = findViewById(R.id.NotificationLayoutDoctor)
        txtSearch= findViewById(R.id.TxtSearch)

    listviewPatientForDoctor = findViewById<ListView>(R.id.listPatientForDocteur)
    //listPatientForDoctor.add(ModelPatientList("Mohamed Rouahi",R.drawable.logopatient))
    //listPatientForDoctor.add(ModelPatientList("Mohamed Rouahi",R.drawable.logopatient))
    //listPatientForDoctor.add(ModelPatientList("Mohamed Rouahi",R.drawable.logopatient))
    //listPatientForDoctor.add(ModelPatientList("Mohamed Rouahi",R.drawable.logopatient))
    //listPatientForDoctor.add(ModelPatientList("Mohamed Rouahi",R.drawable.logopatient))
    //listPatientForDoctor.add(ModelPatientList("Mohamed Rouahi",R.drawable.logopatient))
    //listviewPatientForDoctor!!.adapter = MyAdapterListPatientForDoctors(this, R.layout.list_patient_for_doctor, listPatientForDoctor)


    navigationDoctor!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_reclamation -> {
                    reclamationDoctor!!.visibility = View.VISIBLE
                    homeDoctor!!.visibility = View.GONE
                    messageDoctor!!.visibility = View.GONE
                    notificationDoctor!!.visibility = View.GONE
                    slidPanel!!.visibility = View.GONE
                    search!!.visibility = View.VISIBLE

                    var reclamationnav = DoctorReclamationFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentDoctor, reclamationnav).commit()

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {

                    notificationDoctor!!.visibility = View.VISIBLE
                    reclamationDoctor!!.visibility = View.GONE
                    homeDoctor!!.visibility = View.GONE
                    messageDoctor!!.visibility = View.GONE
                    slidPanel!!.visibility = View.GONE
                    search!!.visibility = View.VISIBLE

                    var notificationnav = DoctorNotificationFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentDoctor, notificationnav).commit()



                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_message -> {

                    messageDoctor!!.visibility = View.VISIBLE
                    notificationDoctor!!.visibility = View.GONE
                    reclamationDoctor!!.visibility = View.GONE
                    homeDoctor!!.visibility = View.GONE
                    slidPanel!!.visibility = View.GONE
                    search!!.visibility = View.VISIBLE


                    var messagenav = DoctorMessageFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentDoctor, messagenav).commit()


                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_home -> {
                    homeDoctor!!.visibility = View.VISIBLE
                    messageDoctor!!.visibility = View.GONE
                    notificationDoctor!!.visibility = View.GONE
                    reclamationDoctor!!.visibility = View.GONE
                    slidPanel!!.visibility = View.GONE
                    search!!.visibility = View.VISIBLE

                    var home = AccueilDoctorFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentDoctor, home)
                        .commit()

                }
                R.id.navigation_profile -> {

                    var intent = Intent(this, DoctorProfilActivity::class.java)
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

    }
}