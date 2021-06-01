package com.example.stagepfe.Activity.Patients

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.stagepfe.Activity.Authentication.AuthenticationFragmentContainerActivity
import com.example.stagepfe.Adapters.Doctor.MyAdapterPatientListForDoctorProfil
import com.example.stagepfe.Models.Patient.Model
import com.example.stagepfe.Adapters.Patients.MyAdapter
import com.example.stagepfe.Dao.AppointmentCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Fragments.Patient.MessagePatientFragment
import com.example.stagepfe.Fragments.Patient.NotificationPatintFragment
import com.example.stagepfe.Fragments.Patient.PatientAccountFragment
import com.example.stagepfe.Fragments.Patient.PatientReclamationFragment
import com.example.stagepfe.R
import com.example.stagepfe.entite.Appointment
import com.example.stagepfe.entite.UserItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import java.util.*
import kotlin.collections.ArrayList

class BottomBarPatientActivity : AppCompatActivity() {
    var listview: ListView? = null
    var list = mutableListOf<Model>()
    var adapterListMedecin: MyAdapter? = null
    var showMore: TextView? = null
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
    private val pickImage = 100
    private var imageUri: Uri? = null
    var imageProfilPatient: ImageView? = null
    var nameCurrentUser:String? = null
    var testOnRepeatingDocorName:String= ""
    var SpecialityDoctor:String= ""
    var changeUser:ImageView? = null
    var userDao = UserDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_bar)
        initView()
    }

    private fun initView() {
        search = findViewById(R.id.float_button)
        slidPanel = findViewById(R.id.sliding_layout_Patient)
        downImg = findViewById(R.id.DownIC)
        navigation = findViewById(R.id.bottom_nav)
        titlelTV = findViewById(R.id.TitleActivityBottomnavigation)
        reclamationLayout = findViewById(R.id.reclamationLayout)
        homeLayout = findViewById(R.id.profilInformationLayout)
        messageLayout = findViewById(R.id.MessageLayout)
        notificatioLayout = findViewById(R.id.NotificationLayout)
        txtSearchDoctor= findViewById(R.id.TxtSearchDoctor)
        imageProfilPatient = findViewById(R.id.IVimageProfilPatient)
        listview = findViewById<ListView>(R.id.list)
        showMore = findViewById<TextView>(R.id.VoirPlus)
        changeUser = findViewById(R.id.change_user_patient)

        initAdapter()
//////////////////////////////////////////////////////////////////////////////////////////////////
        var home = PatientAccountFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentPatient, home)
            .commit()
//////////////////////////////////////////////////////////////////////////////////////////////////
        changeUser!!.setOnClickListener {
            dialog()
        }
//////////////////////////////////////////////////////////////////////////////////////////////////


        var userdao = UserDao()
        userdao.populateSearch(object : UserCallback {
          override fun onSuccess(userItem: UserItem) {
         var fullNameDoctor = userItem.prenom + " " + userItem.nom

         if (userItem.role!!.containsAll(listOf("Médecin","Patient") )){
             list.add(Model(fullNameDoctor, userItem.speciality.toString(), R.drawable.doctor_ic))
             listview!!.adapter = MyAdapter(this@BottomBarPatientActivity, R.layout.doctors_list, list)
             }
     }

     override fun failure() {
     }
 })


        listview!!.setOnItemClickListener { parent, view, position, id ->


                var intent = Intent(this, ShowProfilDoctorToPatientActivity::class.java)

                var doctorNameInList = view.findViewById<TextView>(R.id.name_doctor_list)
                intent.putExtra("nameDoctor", doctorNameInList.text.toString())
                startActivity(intent)
//                finish() // If activity no more needed in back stack

        }

//////////////////////////////////////////////////////////////////////////////////////////////////

        imageProfilPatient!!.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 1000)
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                val photoDocteur = userItem.profilPhotos
                Glide
                    .with(this@BottomBarPatientActivity)
                    .load(photoDocteur)
                    .into(imageProfilPatient!!)
            }
            override fun failure() {
            }
        })
//////////////////////////////////////////////////////////////////////////////////////////////////
        navigation!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_reclamation -> {
                    reclamationLayout!!.visibility = VISIBLE
                    homeLayout!!.visibility = GONE
                    messageLayout!!.visibility = GONE
                    notificatioLayout!!.visibility = GONE
                    slidPanel!!.visibility = GONE
                    search!!.visibility = GONE

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
                    slidPanel!!.visibility = GONE
                    search!!.visibility = GONE

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
                    slidPanel!!.visibility = GONE
                    search!!.visibility = VISIBLE

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
                    slidPanel!!.visibility = GONE
                    search!!.visibility = VISIBLE

                    var home = PatientAccountFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentPatient, home)
                        .commit()

                }
                R.id.navigation_profile -> {

                    var intent = Intent(this, ProfilePatientActivity::class.java)
                    startActivity(intent)
//                    finish()

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

    private fun initAdapter() {
        adapterListMedecin= MyAdapter(this@BottomBarPatientActivity, R.layout.doctors_list, list)
           listview !!.adapter =adapterListMedecin
    }

    private fun dialog() {
        var v = View.inflate(this, R.layout.dialogchangeuser, null)
        var builder = AlertDialog.Builder(this)
        builder.setView(v)

        var dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.findViewById<TextView>(R.id.sign_out_tv).setOnClickListener {
            signout()
        }

    }

    private fun signout() {
        var userDao = UserDao()
        userDao.signOut(UserItem(),object : UserCallback {
            override fun onSuccess(userItem: UserItem) {

                var intent = Intent(this@BottomBarPatientActivity,
                    AuthenticationFragmentContainerActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)//makesure user cant go back
                startActivity(intent)
                finish()
            }

            override fun failure() {
                var toast= Toast.makeText(this@BottomBarPatientActivity,"probleme",Toast.LENGTH_SHORT)
                toast.show()
            }
        })
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

    ///////////////////////////////////////////////////////////////////////////////////////////////
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == 1000) {
            imageUri = data?.data
            imageProfilPatient!!.setImageURI(imageUri)
            imageUri = data?.data
            imageProfilPatient!!.setImageURI(imageUri)

            userDao.retrieveCurrentDataUser(object : UserCallback {
                override fun onSuccess(userItem: UserItem) {
                    userDao.uploadImageToFirebase(
                        userItem.id.toString(),
                        imageUri!!)
                }

                override fun failure() {
                }
            })

        }else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////

}