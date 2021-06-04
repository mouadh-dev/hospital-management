package com.example.stagepfe.Activity.AgentLabo

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.example.stagepfe.Activity.Authentication.AuthenticationFragmentContainerActivity
import com.example.stagepfe.Activity.Pharmacien.ProfilPharmacienActivity
import com.example.stagepfe.Adapters.Doctor.MyAdapterAnalyseReading
import com.example.stagepfe.Adapters.Doctor.MyAdapterOrdonancePharmacien
import com.example.stagepfe.Adapters.Doctor.MyAdapterOrdonnanceAgentLabo
import com.example.stagepfe.Dao.ResponseCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Fragments.AgentLabo.AgentAccueilFragment
import com.example.stagepfe.Fragments.AgentLabo.AgentReclamationFragment
import com.example.stagepfe.Fragments.Doctor.DoctorReclamationFragment
import com.example.stagepfe.Fragments.Pharmacien.AccueilPharmacienFragment
import com.example.stagepfe.R
import com.example.stagepfe.entite.AnalyseOrdonnance
import com.example.stagepfe.entite.MedicamentOrdonance
import com.example.stagepfe.entite.Ordonance
import com.example.stagepfe.entite.UserItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import java.util.*

class AccueilAgentLaboActivity : AppCompatActivity() {
    companion object {
        val TAG = "AccueilAgentLaboActivity"
    }
    var navigationAgent: BottomNavigationView? = null
    // private val pickImage = 100
    private var imageUri: Uri? = null
    var imageProfilAgent: ImageView? = null
    var reclamationAgent: LinearLayout? = null
    var homeAgent: LinearLayout? = null
    var changeUser:ImageView? = null
    var cameraButtonAnalyse: ImageView? = null
    var text: String? = ""
    val listAnalyse = mutableListOf<AnalyseOrdonnance>()
    var adapterAnalyse: MyAdapterOrdonnanceAgentLabo? = null
    var ordonanceToChange = Ordonance()
    var userDao = UserDao()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
          //profile photo
            if (requestCode == 1000) {
                imageUri = data?.data
                imageProfilAgent!!.setImageURI(imageUri)

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
        if (resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {

                    var ordonance = Gson().fromJson(result.contents, Ordonance::class.java)
                    val v = View.inflate(this, R.layout.dialog_ordonance, null)
                    val builder = AlertDialog.Builder(this)
                    builder.setView(v)
                    val dialog = builder.create()
                    dialog.show()
                    var listView = dialog.findViewById<ListView>(R.id.List_Medicament_to_show)

                    listView.visibility = View.VISIBLE
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    userDao.populateSearch(object : UserCallback {
                        override fun onSuccess(userItem: UserItem) {
                            if (userItem.id.equals(ordonance.idDoctor))
                                dialog.findViewById<TextView>(R.id.nameDoctor)
                                    .setText("DR" + " " + userItem.nom + " " + userItem.prenom)

                        }

                        override fun failure() {
                        }
                    })
                    userDao.populateSearch(object : UserCallback {
                        override fun onSuccess(userItem: UserItem) {
                            if (userItem.id.equals(ordonance.idPatient))
                                dialog.findViewById<TextView>(R.id.namePatient)
                                    .setText(userItem.nom + " " + userItem.prenom)

                        }

                        override fun failure() {
                        }
                    })

                    dialog.findViewById<Button>(R.id.btn_remove).setText("D'accord")
                    for (analyse in ordonance.analyse) {
                        println("mouadh :: "+ analyse.descriptionAnalyse.toString())
                        listAnalyse.add(analyse)
                    }
                    adapterAnalyse = MyAdapterOrdonnanceAgentLabo(
                        this,
                        R.layout.ordonnance_analyse_to_agent,
                        listAnalyse
                    )
                    listView!!.adapter = adapterAnalyse
                    adapterAnalyse!!.notifyDataSetChanged()
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE)
                    listView.setOnItemClickListener { parent, view, position, id ->


                    }
                    dialog.setCancelable(false)
                    dialog.findViewById<Button>(R.id.btn_remove).setOnClickListener {

                        var text = ""
                        for (i in 0 until adapterAnalyse!!.count) {
                            var view = adapterAnalyse!!.getView(
                                i,
                                findViewById<LinearLayout>(R.id.checkBox_Agent),
                                listView!!
                            )
                            val analyse: AnalyseOrdonnance = listAnalyse.get(i)
                            if (!analyse.isSelectedAnalyse!!) {
                                val analyse: AnalyseOrdonnance =
                                    adapterAnalyse!!.getItem(i) as AnalyseOrdonnance
                                text += analyse.descriptionAnalyse + "\n"
//                                nonCheck += medicament.nameMedicament + "\n" + medicament.quantity + " " + "fois par jours" + "\n" + medicament.description + "\n"
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
                        listAnalyse.clear()
                    }
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil_agent_labo)
        var homeLaboratoire = AgentAccueilFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentAgent, homeLaboratoire).commit()
        initView()
    }

    private fun initView() {
        navigationAgent = findViewById(R.id.bottom_nav_Agent)
        imageProfilAgent = findViewById(R.id.IVimageProfilAgent)
        reclamationAgent = findViewById(R.id.reclamationLayoutAgent)
        homeAgent = findViewById(R.id.profilInformationAgent)
        changeUser = findViewById(R.id.change_user_labo)
        cameraButtonAnalyse = findViewById(R.id.float_button_camera_agent)


        //////////////////////////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////////////////////////

          imageProfilAgent!!.setOnClickListener {
              val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
              startActivityForResult(gallery, 1000)
          }

        changeUser!!.setOnClickListener {
            dialog()
        }
//////////////////////////////////////////////////////////////////////////////////////////////////

        navigationAgent!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home_phar -> {
                    homeAgent!!.visibility = View.VISIBLE
                    reclamationAgent!!.visibility = View.GONE

                    var homeLaboratoire = AgentAccueilFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentAgent, homeLaboratoire).commit()

                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_profile_phar -> {

                    var intent = Intent(this, ProfilAgentLaboActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                R.id.navigation_reclamation_phar -> {
                    reclamationAgent!!.visibility = View.VISIBLE
                    homeAgent!!.visibility = View.GONE


                    var reclamationnav = AgentReclamationFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentAgent, reclamationnav).commit()

                    return@OnNavigationItemSelectedListener true
                }
            }
            true
        })
        cameraButtonAnalyse!!.setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            scanner.setBeepEnabled(false)
            scanner.initiateScan()
        }
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

                var intent = Intent(this@AccueilAgentLaboActivity,
                    AuthenticationFragmentContainerActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)//makesure user cant go back
                startActivity(intent)
                finish()
            }

            override fun failure() {
                var toast= Toast.makeText(this@AccueilAgentLaboActivity,"probleme", Toast.LENGTH_SHORT)
                toast.show()
            }
        })
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////




    fun getMyDataAgentLabo(): String? {
        return ordonanceToChange.idPatient
    }

    override fun onResume() {
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                val PhotoAgent = userItem.profilPhotos
                Glide
                    .with(this@AccueilAgentLaboActivity)
                    .load(PhotoAgent)
                    .into(imageProfilAgent!!)

            }

            override fun failure() {
            }
        })
        super.onResume()
    }
}