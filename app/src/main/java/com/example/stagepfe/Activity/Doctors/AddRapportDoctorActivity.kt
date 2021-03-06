package com.example.stagepfe.Activity.Doctors

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.Dao.ResponseCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Notification
import com.example.stagepfe.entite.Rapports
import com.example.stagepfe.entite.UserItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddRapportDoctorActivity : AppCompatActivity() {
    private var TextRapport: EditText? = null
    private var addRapport: Button? = null
    private var nameDoctorRapportET: EditText? = null
    var userItem = UserItem()
    var userDao = UserDao()
    var rapports = Rapports()
    var notification = Notification()
    var idDoctor: String? = null
    var idPatient: String? = null
    var nameDoctorRapport: String? = null
    var namePatientRapport: String? = null
    var speciality: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    val currentDateTime = LocalDateTime.now()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_rapport_doctor)
        initView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initView() {
        TextRapport = findViewById(R.id.text_Rapport_Et)
        addRapport = findViewById(R.id.Add_Rapport_Button)
        nameDoctorRapportET = findViewById(R.id.Name_Doctor_rapport)

        var patient = intent.getStringExtra("namePatentToRapport")
        nameDoctorRapportET!!.setText(patient)
        nameDoctorRapportET!!.isFocusable = false

        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                nameDoctorRapport = userItem.prenom + " " + userItem.nom
                idDoctor = userItem.id.toString()
                speciality = userItem.speciality.toString()
            }

            override fun failure() {
            }
        })



        addRapport!!.setOnClickListener {
            if (TextRapport!!.text.isEmpty()) {
                var v = View.inflate(
                    this@AddRapportDoctorActivity,
                    R.layout.fragment_dialog,
                    null
                )
                var builder = AlertDialog.Builder(this@AddRapportDoctorActivity)
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                    dialog.dismiss()
                }
            } else {
                userDao.populateSearch(object : UserCallback {
                    override fun onSuccess(userItem: UserItem) {
                        var fullName = userItem.nom + " " + userItem.prenom
                        if (patient.equals(fullName)) {
                            idPatient = userItem.id
                            rapports.idPatientRapport = idPatient
                            rapports.textRapport = TextRapport!!.text.toString()
                            rapports.idDoctorRapport = idDoctor
                            rapports.specialityDoctor = speciality
                            rapports.dateRapport = currentDateTime.format(DateTimeFormatter.ISO_DATE)
                            rapports.hourRapport = currentDateTime.format(DateTimeFormatter.ISO_TIME)

                            notification.idDoctor = idDoctor
                            notification.idPatient = idPatient
                            notification.timeNotification = currentDateTime.format(DateTimeFormatter.ISO_TIME)
                            notification.dateNotification = currentDateTime.format(DateTimeFormatter.ISO_DATE)
                            notification.type = "rapport"

                            userDao.insertRapport(
                                rapports,
                                userItem,
                                idPatient!!,
                                idDoctor!!,
                                notification,
                                object : ResponseCallback {
                                    override fun success() {
                                        dialog()
                                    }

                                    override fun success(medicament: String) {
                                    }

                                    override fun failure() {
                                    }
                                })
                        }
                    }

                    override fun failure() {
                    }
                })

            }
        }
    }

    private fun dialog() {
        var v = View.inflate(
            this@AddRapportDoctorActivity,
            R.layout.fragment_dialog,
            null
        )
        var builder = AlertDialog.Builder(this@AddRapportDoctorActivity)
        builder.setView(v)

        var dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.findViewById<TextView>(R.id.msgdialog).visibility = GONE
        dialog.findViewById<TextView>(R.id.TitleDialog).text =
            "votre rapport a ??t?? cr???? avec succ??s"
        dialog.findViewById<ImageView>(R.id.CheckDialog)
            .setBackgroundResource(R.drawable.ellipse_green)
        dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            dialog.dismiss()
            finish()
        }
    }

}