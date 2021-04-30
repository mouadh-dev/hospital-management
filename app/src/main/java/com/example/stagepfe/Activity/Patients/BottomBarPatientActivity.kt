package com.example.stagepfe.Activity.Patients

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_bar)
        initView()
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
        imageProfilPatient = findViewById(R.id.IVimageProfilPatient)
        listview = findViewById<ListView>(R.id.list)
        showMore = findViewById<TextView>(R.id.VoirPlus)

//////////////////////////////////////////////////////////////////////////////////////////////////
        var home = PatientAccountFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentPatient, home)
            .commit()
//////////////////////////////////////////////////////////////////////////////////////////////////

//        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
//        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
//        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
//        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
//        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
//        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
//        list.add(Model("Dr Foulen Fouleni", "Generaliste", R.drawable.doctor_ic))
//        listview!!.adapter = MyAdapter(this, R.layout.doctors_list, list)
        //////////////////////////////////////////////////////////////////////////////////////////////////

        var userdao = UserDao()
        var userItem=UserItem()
 userdao.populateSearch(userItem, object : UserCallback {
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
                finish() // If activity no more needed in back stack

        }

//////////////////////////////////////////////////////////////////////////////////////////////////

        imageProfilPatient!!.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
            //   updateImageProfil()

        }
//////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////



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

   // private fun updateImageProfil() {
    //  val user = auth.currentUser
   // user?.let { user ->
   //  val photoURI = Uri.parse("android.resource://$packageName/${R.drawable.logopatient}")
    //  val profileUpdates = UserProfileChangeRequest.Builder()
    //     .setPhotoUri(photoURI)
    //    .build()
    //  CoroutineScope(Dispatchers.IO).launch {
    //   try {
    //      user.updateProfile(profileUpdates).await()
    //      withContext(Dispatchers.Main) {
    //         Toast.makeText(this@BottomBarPatientActivity, "Successfully updated profile",
    //   Toast.LENGTH_LONG).show()
    //        }
    //    } catch(e: Exception) {
    //        withContext(Dispatchers.Main) {
    //          Toast.makeText(this@BottomBarPatientActivity, e.message, Toast.LENGTH_LONG).show()
    //      }
    //     }

    //    }
//  }
    //  }

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
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageProfilPatient!!.setImageURI(imageUri)
            // uploadImageToFirebase(imageUri)
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////Storage image ///////////////////////////////////////////////////////
   // private fun uploadImageToFirebase(imageUri: Uri?) {
   // if (imageUri != null) {
    // val fileName = UUID.randomUUID().toString() +".jpg"

    // val database = FirebaseDatabase.getInstance()
    // val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

    // refStorage.putFile(imageUri)
    //  .addOnSuccessListener(
    //  OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
        //    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
    //         val imageUrl = it.toString()
    //     }
    //  })

    //  ?.addOnFailureListener(OnFailureListener { e ->
    //       print(e.message)
    //   })
    //  }
    // }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
}