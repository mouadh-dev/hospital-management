package com.example.stagepfe.Activity.Doctors

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.stagepfe.Activity.Authentication.AuthenticationFragmentContainerActivity
import com.example.stagepfe.Activity.Patients.ShowProfilDoctorToPatientActivity
import com.example.stagepfe.Adapters.Doctor.MyAdapterListPatientForDoctors
import com.example.stagepfe.Adapters.Doctor.MyAdapterPatientListForDoctorProfil
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Fragments.Doctor.AccueilDoctorFragment
import com.example.stagepfe.Fragments.Doctor.DoctorMessageFragment
import com.example.stagepfe.Fragments.Doctor.DoctorNotificationFragment
import com.example.stagepfe.Fragments.Doctor.DoctorReclamationFragment
import com.example.stagepfe.Models.Doctors.ModelPatientList
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import com.nightonke.boommenu.BoomButtons.BoomButton
import com.nightonke.boommenu.BoomButtons.HamButton
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
    private var imageUri: Uri? = null
    var imageProfilDoctor: ImageView? = null
    var changeUser:ImageView? = null
    var adapterListPatientforDoctorAccueil: MyAdapterListPatientForDoctors? = null
    var userDao = UserDao()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_doctor)

        var home = AccueilDoctorFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentDoctor, home).commit()
        initView()
//
    }


    private fun initView() {
        changeUser = findViewById(R.id.change_user)
        search = findViewById(R.id.float_button_Medecin)
        slidPanel = findViewById(R.id.sliding_layout)
        downImg = findViewById(R.id.DownIC)
        navigationDoctor = findViewById(R.id.bottom_nav_Doctor)
        titlelTV = findViewById(R.id.TitleActivityBottomnavigation)
        reclamationDoctor = findViewById(R.id.reclamationLayoutDoctor)
        homeDoctor = findViewById(R.id.profilInformationDoctor)
        messageDoctor = findViewById(R.id.MessageLayoutDoctor)
        notificationDoctor = findViewById(R.id.NotificationLayoutDoctor)
        txtSearch= findViewById(R.id.TxtSearch)
        imageProfilDoctor = findViewById(R.id.IVimageProfilDoctor)
        listviewPatientForDoctor = findViewById<ListView>(R.id.listPatientForDocteur)

        initAdapter()

        var userdao = UserDao()
        userdao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                var fullNamePatient = userItem.nom + " " + userItem.prenom
                if (userItem.role!!.contains("Patient")){
                    listPatientForDoctor.add(ModelPatientList(fullNamePatient, R.drawable.logopatient))
                    listviewPatientForDoctor!!.adapter = MyAdapterListPatientForDoctors(
                        this@AccountDoctorActivity, R.layout.list_patient_for_doctor, listPatientForDoctor)}
            }
            override fun failure() {
            }
        })
         listviewPatientForDoctor!!.setOnItemClickListener { parent, view, position, id ->
         var intent = Intent(this, ShowInfoPatientForDoctorActivity::class.java)
          var patintNameInList = view.findViewById<TextView>(R.id.TVnamePatientList)
          intent.putExtra("nomPatient", patintNameInList.text.toString())
          startActivity(intent)
//          finish() // If activity no more needed in back stack

         }
//////////////////////////////////////////////////////////////////////////////////////////////////
        imageProfilDoctor!!.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 1000)
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////////////////////////////////////////
        changeUser!!.setOnClickListener {
            dialog()
        }
//////////////////////////////////////////////////////////////////////////////////////////////////

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
                    supportFragmentManager.beginTransaction().replace(
                        R.id.ContainerFragmentDoctor,
                        home
                    )
                        .commit()

                }
                R.id.navigation_profile -> {

                    var intent = Intent(this, DoctorProfilActivity::class.java)
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
        adapterListPatientforDoctorAccueil=MyAdapterListPatientForDoctors(
            this@AccountDoctorActivity, R.layout.list_patient_for_doctor, listPatientForDoctor)
        listviewPatientForDoctor!!.adapter =adapterListPatientforDoctorAccueil
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


    //////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
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
                        this@AccountDoctorActivity,
                        android.R.layout.simple_list_item_1,
                        names
                    )
                    txtSearch!!.setAdapter(adapter)
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
            imageProfilDoctor!!.setImageURI(imageUri)
            imageUri = data?.data
            imageProfilDoctor!!.setImageURI(imageUri)

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
    private fun signout() {
        var userDao = UserDao()
        userDao.signOut(UserItem(),object : UserCallback {
            override fun onSuccess(userItem: UserItem) {

                var intent = Intent(this@AccountDoctorActivity,
                    AuthenticationFragmentContainerActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)//makesure user cant go back
                startActivity(intent)
                finish()
            }

            override fun failure() {
                var toast= Toast.makeText(this@AccountDoctorActivity,"probleme",Toast.LENGTH_SHORT)
                toast.show()
            }
        })
    }

    override fun onResume() {
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                if(userItem.profilPhotos != null) {
                    Glide
                        .with(this@AccountDoctorActivity)
                        .load(userItem.profilPhotos)
                        .into(imageProfilDoctor!!)
                }
            }
            override fun failure() {
            }
        })
        super.onResume()
    }
}