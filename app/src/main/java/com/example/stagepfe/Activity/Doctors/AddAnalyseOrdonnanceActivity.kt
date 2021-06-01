package com.example.stagepfe.Activity.Doctors

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.isEmpty
import com.example.stagepfe.Adapters.Doctor.MyAdapterAnalyseReading
import com.example.stagepfe.Dao.OrdonanceCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.AnalyseOrdonnance

import com.example.stagepfe.entite.Ordonance
import com.example.stagepfe.entite.UserItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddAnalyseOrdonnanceActivity : AppCompatActivity() {
    private var TextAnalyse: EditText? = null
    private var addAnalyse: Button? = null
    private var nameDoctorAnalyseET: EditText? = null
    var returnBack: ImageView? = null
    var textAnalyses: EditText? = null
    var listViewOrdAnalyse: ListView? = null
    var listOrdAnalyse = mutableListOf<AnalyseOrdonnance>()
    var listMedicamentOrdonanceAnalyse = arrayListOf<AnalyseOrdonnance>()

    var nameDoctorAnaylse: String? = null
    var userItem = UserItem()
    var idDoctor: String? = null
    var namePatientAnaylse: String? = null
    var idPAtient: String? = null
    var userDao = UserDao()
    var speciality: String? = null
    private var confirmerAnalyse: Button? = null
    var user = UserItem()
    private var envoyerAnalyse: Button? = null
    var ordonance = Ordonance()
    var idPatient:String? = null
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDateTime = LocalDateTime.now()
    private var analyseAdapter: MyAdapterAnalyseReading? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_analyse_ordonnance)
        initView()
    }

    private fun initView() {
        TextAnalyse = findViewById(R.id.text_Analyse_Et)
        addAnalyse = findViewById(R.id.Add_Analyse_Button)
        nameDoctorAnalyseET = findViewById(R.id.Name_Doctor_analyse)
        returnBack = findViewById(R.id.return_back_Ord_analyse)
        textAnalyses = findViewById(R.id.text_Analyse_Et)
        confirmerAnalyse = findViewById(R.id.Confirmer_Analyse_Button)
        envoyerAnalyse = findViewById(R.id.Add_Analyse_Button)
        listViewOrdAnalyse = findViewById(R.id.ListOrdoAnalyse)
        initAdapter()
        var patient = intent.getStringExtra("namePatentToOrdonance")
        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                var fullName = userItem.nom + " " + userItem.prenom
                nameDoctorAnalyseET!!.setText(fullName)
                if (patient.equals(fullName)) {
                    idPatient = userItem.id
                }
            }

            override fun failure() {
            }
        })

        nameDoctorAnalyseET!!.isFocusable = false

        returnBack!!.setOnClickListener {
            finish()
        }

        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                nameDoctorAnaylse = userItem.prenom + " " + userItem.nom
                idDoctor = userItem.id.toString()
                speciality = userItem.speciality.toString()
            }

            override fun failure() {
            }
        })
        ////////////////////////////////////////////listView Analyse////////////////////////////////////
        confirmerAnalyse!!.setOnClickListener {
            if (textAnalyses!!.text.isEmpty()) {
                var text = "veuillez ajouter des analyses"
                dialog(text)
            } else {
                var analyseOrdonnance = AnalyseOrdonnance()
                analyseOrdonnance.descriptionAnalyse = textAnalyses!!.text.toString()
                listOrdAnalyse.add(analyseOrdonnance)

                analyseAdapter!!.notifyDataSetChanged()

                textAnalyses!!.text.clear()
            }
        }
        ////////////////////////////////////////////add ordonance tofirebase////////////////////////////////
         userDao.retrieveCurrentDataUser(
          object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                idDoctor = userItem.id.toString()
            nameDoctorAnaylse = userItem.prenom + " " + userItem.nom
          userDao.populateSearch(object : UserCallback {
             @SuppressLint("NewApi")
              override fun onSuccess(userItem: UserItem) {
                 var patient = intent.getStringExtra("namePatentToOrdonance")
                 var fullname = userItem.nom + " " + userItem.prenom
                 if (patient.equals(fullname)) {
                     namePatientAnaylse = patient
                     idPAtient = userItem.id.toString()
                     println("mouadh :: " + namePatientAnaylse + " !! " + idPAtient)
                     ordonance.idDoctor = idDoctor
//                      ordonance.nameDoctorOrd = nameDoctorAnaylse
//                      ordonance.namepatientOrdo = namePatientAnaylse
                     ordonance.idPatient = idPAtient
                     ordonance.analyse = listMedicamentOrdonanceAnalyse
                      ordonance.taken = "pas encore"
                      ordonance.color = Color.RED.toString()
                     ordonance.typeOrdonnance= "Ordonnance analyse"
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        ordonance.dateOrdonanceSend =
                           currentDateTime.format(DateTimeFormatter.ISO_DATE)
                     }
                      ordonance.hourOrdonanceSend =
                          currentDateTime.format(DateTimeFormatter.ISO_TIME)
                }

                }

              override fun failure() {

                      }
         })
    }

         override fun failure() {
        }
        })
        envoyerAnalyse!!.setOnClickListener {
            if (listViewOrdAnalyse!!.isEmpty()) {
           var text = "veuillez ajouter des analyses"
                dialog(text)

            } else {
                filAnalyse()
                 userDao.insertordonance(idDoctor!!, idPAtient!!, ordonance, user,
                 object : OrdonanceCallback {
                     override fun successOrdonance(ordonance: Ordonance) {
                               startActivity(Intent(this@AddAnalyseOrdonnanceActivity,ShowInfoPatientForDoctorActivity::class.java))
                        Toast.makeText(
                              applicationContext,
                              "add ordo avec success",
                              Toast.LENGTH_SHORT
                         ).show()
                      }
//
                                      override fun failureOrdonance() {
//
                                      }
                  })
               }
                }
}

    private fun filAnalyse() {
        for (i in 0 until analyseAdapter!!.count) {
            val item = AnalyseOrdonnance() // new one
            analyseAdapter!!.getItem(i)
            var view = analyseAdapter!!.getView(
                i,
                findViewById<TextView>(R.id.description_analyse_list),
                listViewOrdAnalyse!!
            )

            item.descriptionAnalyse =
                view.findViewById<TextView>(R.id.description_analyse_list).text.toString()
            listMedicamentOrdonanceAnalyse.add(item)
        }
    }


    private fun dialog(text: String) {
        var v = View.inflate(this, R.layout.fragment_dialog, null)
        var builder = AlertDialog.Builder(this)
        builder.setView(v)

        var dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.findViewById<TextView>(R.id.TitleDialog).text = text
        dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE
        dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun initAdapter() {
        analyseAdapter = MyAdapterAnalyseReading(this, R.layout.analyse_add_list, listOrdAnalyse)
        listViewOrdAnalyse!!.adapter = analyseAdapter
    }
}