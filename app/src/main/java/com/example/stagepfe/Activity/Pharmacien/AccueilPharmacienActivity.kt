package com.example.stagepfe.Activity.Pharmacien

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.stagepfe.Activity.Authentication.AuthenticationFragmentContainerActivity
import com.example.stagepfe.Activity.Doctors.DoctorProfilActivity
import com.example.stagepfe.Dao.ResponseCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Fragments.AgentLabo.AgentReclamationFragment
import com.example.stagepfe.Fragments.Doctor.AccueilDoctorFragment
import com.example.stagepfe.Fragments.Doctor.DoctorMessageFragment
import com.example.stagepfe.Fragments.Doctor.DoctorNotificationFragment
import com.example.stagepfe.Fragments.Doctor.DoctorReclamationFragment
import com.example.stagepfe.Fragments.Pharmacien.AccueilPharmacienFragment
import com.example.stagepfe.Fragments.Pharmacien.PharmacienReclamationFragment
import com.example.stagepfe.R
import com.example.stagepfe.entite.ProfilPhoto
import com.example.stagepfe.entite.UserItem
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.util.*

class AccueilPharmacienActivity : AppCompatActivity() {

    var navigationPharmacien: BottomNavigationView? = null
    private val pickImage = 100
    private var imageUri: Uri? = null
    var imageProfilPharmacien: ImageView? = null
    var reclamationPharmacien: LinearLayout? = null
    var homePharmacien: LinearLayout? = null
    var changeUser:ImageView? = null
    var userItem = UserItem()
    var userDao = UserDao()
    //var profilPhotos= ProfilPhoto()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil_pharmacien)
        var homePharmacie = AccueilPharmacienFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentPharmacien, homePharmacie).commit()
        initView()
    }

    private fun initView() {
        navigationPharmacien = findViewById(R.id.bottom_nav_Pharmacien)
        imageProfilPharmacien = findViewById(R.id.IVimageProfilPharmacien)
        reclamationPharmacien = findViewById(R.id.reclamationLayoutPharmacien)
        homePharmacien = findViewById(R.id.profilInformationPharmacien)
        changeUser = findViewById(R.id.change_user_pharmacien)

        imageProfilPharmacien!!.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
            //updateImageProfil()

           // userDao.insertPhoto(profilPhotos, userItem, object : ResponseCallback {
            //   override fun success() {
            //      dialog()
           //  }
            //  override fun success(medicament: String) {
            //  }
        //  override fun failure() {
            //  }
            //   })

        }
//////////////////////////////////////////////////////////////////////////////////////////////////
        changeUser!!.setOnClickListener {
            dialog()
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////

        navigationPharmacien!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home_phar -> {
                    homePharmacien!!.visibility = View.VISIBLE
                    reclamationPharmacien!!.visibility = View.GONE
                    var homeParmacien = AccueilPharmacienFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentPharmacien, homeParmacien).commit()

                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_profile_phar -> {

                    var intent = Intent(this, ProfilPharmacienActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                R.id.navigation_reclamation_phar -> {
                    reclamationPharmacien!!.visibility = View.VISIBLE
                    homePharmacien!!.visibility = View.GONE


                    var reclamationnav = PharmacienReclamationFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentPharmacien, reclamationnav).commit()

                    return@OnNavigationItemSelectedListener true
                }
            }
            true
        })
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

                var intent = Intent(this@AccueilPharmacienActivity,
                    AuthenticationFragmentContainerActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)//makesure user cant go back
                startActivity(intent)
                finish()
            }

            override fun failure() {
                var toast= Toast.makeText(this@AccueilPharmacienActivity,"probleme", Toast.LENGTH_SHORT)
                toast.show()
            }
        })
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageProfilPharmacien!!.setImageURI(imageUri)
            uploadImageToFirebase(imageUri)
        }
    }
    ////////////////////////////////////Storage image ///////////////////////////////////////////////////////

        private fun uploadImageToFirebase(imageUri: Uri?) {
        if (imageUri != null) {
        val fileName = UUID.randomUUID().toString() +".jpg"

         val database = FirebaseDatabase.getInstance()
         val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

         refStorage.putFile(imageUri)
         .addOnSuccessListener(
          OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
          taskSnapshot.storage.downloadUrl.addOnSuccessListener {
              val imageUrl = it.toString()
              //saveToFirebaseDataBase(it.toString())
           }
          })

          ?.addOnFailureListener(OnFailureListener { e ->
               print(e.message)
           })
          }


         }

    //private fun saveToFirebaseDataBase(imageUri:String) {
      //  val mAuth = FirebaseAuth.getInstance()
        //val userRef = FirebaseDatabase.getInstance().getReference("users")
        //userRef.setValue(mAuth, imageUri)
          //  .addOnSuccessListener {

            //}
            //.addOnFailureListener {

            //}
    //}


}
