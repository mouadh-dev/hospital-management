package com.example.stagepfe.Activity.Pharmacien

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.stagepfe.Activity.Authentication.AuthenticationFragmentContainerActivity
import com.example.stagepfe.Adapters.Doctor.MyAdapterOrdonancePharmacien
import com.example.stagepfe.Dao.ResponseCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Fragments.Pharmacien.AccueilPharmacienFragment
import com.example.stagepfe.Fragments.Pharmacien.PharmacienReclamationFragment
import com.example.stagepfe.R
import com.example.stagepfe.entite.MedicamentOrdonance
import com.example.stagepfe.entite.Ordonance
import com.example.stagepfe.entite.UserItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import java.util.*


class AccueilPharmacienActivity : AppCompatActivity() {

    var navigationPharmacien: BottomNavigationView? = null
    private val pickImage = 100
    private var imageUri: Uri? = null
    var imageProfilPharmacien: ImageView? = null
    var reclamationPharmacien: LinearLayout? = null
    var homePharmacien: LinearLayout? = null
    var changeUser: ImageView? = null
    var userItem = UserItem()
    var userDao = UserDao()
    var cameraButton: ImageView? = null
    var adapterMedicament: MyAdapterOrdonancePharmacien? = null
    var nonCheck: Ordonance? = null
    var ordonanceToChange = Ordonance()

    val listMedicament = mutableListOf<MedicamentOrdonance>()

    // Get the results:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            //profile photo
            if (requestCode == 1000) {
                imageUri = data?.data
                imageProfilPharmacien!!.setImageURI(imageUri)

                userDao.retrieveCurrentDataUser(object : UserCallback {
                    override fun onSuccess(userItem: UserItem) {
                        userDao.uploadImageToFirebase(
                            userItem.id.toString(),
                            imageUri!!)
                    }

                    override fun failure() {
                    }
                })


            }
            //qrCode

            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {

                    var ordonance = Gson().fromJson(result.contents, Ordonance::class.java)
                    nonCheck = Gson().fromJson(result.contents, Ordonance::class.java)
                    val v = View.inflate(this, R.layout.dialog_ordonance, null)
                    val builder = AlertDialog.Builder(this)
                    builder.setView(v)
                    val dialog = builder.create()
                    dialog.show()
                    var listView = dialog.findViewById<ListView>(R.id.List_Medicament_to_show)
                    listView.visibility = VISIBLE
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    userDao.populateSearch(object : UserCallback {
                        override fun onSuccess(userDoctor: UserItem) {
                            if (userItem.id.equals(ordonance.idDoctor))
                            dialog.findViewById<TextView>(R.id.nameDoctor)
                                .setText("DR" + " " + userDoctor.nom + " " + userDoctor.prenom)
                        }

                        override fun failure() {
                        }
                    })
                    userDao.populateSearch(object : UserCallback {
                        override fun onSuccess(userPatient: UserItem) {
                            if (userItem.id.equals(ordonance.idPatient))
                                dialog.findViewById<TextView>(R.id.namePatient)
                                    .setText(userPatient.nom + " " + userPatient.prenom)
                        }

                        override fun failure() {
                        }
                    })

                    dialog.findViewById<Button>(R.id.btn_remove).setText("D'accord")
                    for (medicament in ordonance.medicament) {
                        listMedicament.add(medicament)
                    }
                    adapterMedicament = MyAdapterOrdonancePharmacien(
                        this,
                        R.layout.ordonance_to_pharmacien,
                        listMedicament
                    )
                    dialog.findViewById<ListView>(R.id.List_Medicament_to_show)!!.adapter =
                        adapterMedicament
                    adapterMedicament!!.notifyDataSetChanged()
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE)
                    listView.setOnItemClickListener { parent, view, position, id ->


                    }
                    dialog.setCancelable(false)
                    dialog.findViewById<Button>(R.id.btn_remove).setOnClickListener {
                        var text = ""
                        for (i in 0 until adapterMedicament!!.count) {
                            val medicmaent: MedicamentOrdonance = listMedicament.get(i)
                            if (!medicmaent.isSelected!!) {
                                val medicament: MedicamentOrdonance =
                                    adapterMedicament!!.getItem(i) as MedicamentOrdonance
                                text += medicament.nameMedicament + "\n" + medicament.quantity + " " + "fois par jours" + "\n" + medicament.description + "\n"
                            }

                        }

                        ordonanceToChange.color = Color.GREEN.toString()
                        ordonanceToChange.taken = "termineé"
//                        ordonanceToChange.namepatientOrdo = ordonance.namepatientOrdo
                        ordonanceToChange.medicament = ordonance.medicament
                        ordonanceToChange.id = ordonance.id
                        ordonanceToChange.idDoctor = ordonance.idDoctor
                        ordonanceToChange.idPatient = ordonance.idPatient
//                        ordonanceToChange.nameDoctorOrd = ordonance.nameDoctorOrd
                        ordonanceToChange.hourOrdonanceSend = ordonance.hourOrdonanceSend
                        ordonanceToChange.dateOrdonanceSend = ordonance.dateOrdonanceSend
                        ordonanceToChange.analyse = ordonance.analyse
                        ordonanceToChange.typeOrdonnance = ordonance.typeOrdonnance

                        userDao.updateOrdonance(ordonance.idDoctor.toString(),
                            ordonance.idPatient.toString(),
                            ordonance.id.toString(),
                            ordonanceToChange,
                            object : ResponseCallback {
                                override fun success(medicament: String) {

                                }

                                override fun success() {
                                }

                                override fun failure() {
                                }

                            })
                        text = ""
                        dialog.dismiss()
                    }
                    dialog.setOnDismissListener {
                        listMedicament.clear()
                    }
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil_pharmacien)
        var homePharmacie = AccueilPharmacienFragment()
        supportFragmentManager.beginTransaction().replace(
            R.id.ContainerFragmentPharmacien,
            homePharmacie
        ).commit()
        initView()
    }

    private fun initView() {
        navigationPharmacien = findViewById(R.id.bottom_nav_Pharmacien)
        imageProfilPharmacien = findViewById(R.id.IVimageProfilPharmacien)
        reclamationPharmacien = findViewById(R.id.reclamationLayoutPharmacien)
        homePharmacien = findViewById(R.id.profilInformationPharmacien)
        changeUser = findViewById(R.id.change_user_pharmacien)
        cameraButton = findViewById(R.id.float_button_camera)


//////////////////////////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////////////////////////
        imageProfilPharmacien!!.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 1000)
        }
//////////////////////////////////////////////////////////////////////////////////////////////////
        cameraButton!!.setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            scanner.setBeepEnabled(false)
            scanner.initiateScan()
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////
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

    fun getMyDataPharmacien(): String? {
        return ordonanceToChange.idPatient
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
        userDao.signOut(UserItem(), object : UserCallback {
            override fun onSuccess(userItem: UserItem) {

                var intent = Intent(
                    this@AccueilPharmacienActivity,
                    AuthenticationFragmentContainerActivity::class.java
                )
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)//makesure user cant go back
                startActivity(intent)
                finish()
            }

            override fun failure() {
                var toast = Toast.makeText(
                    this@AccueilPharmacienActivity,
                    "probleme",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        })
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    override fun onResume() {
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                if(userItem.profilPhotos != null) {
                    Glide
                        .with(this@AccueilPharmacienActivity)
                        .load(userItem.profilPhotos)
                        .into(imageProfilPharmacien!!)
                }
            }
            override fun failure() {
            }
        })
        super.onResume()
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
}
