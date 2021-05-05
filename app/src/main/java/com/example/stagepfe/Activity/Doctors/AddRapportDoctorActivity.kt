package com.example.stagepfe.Activity.Doctors

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.stagepfe.Dao.ResponseCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Rapports
import com.example.stagepfe.entite.UserItem

class AddRapportDoctorActivity : AppCompatActivity() {
    private var TextRapport: EditText? = null
    private var addRapport: Button? = null
    private var nameDoctorRapportET: EditText? = null
    var userItem = UserItem()
    var userDao = UserDao()
    var rapports= Rapports()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_rapport_doctor)
        initView()
    }

    private fun initView() {
        TextRapport = findViewById(R.id.text_Rapport_Et)
        addRapport = findViewById(R.id.Add_Rapport_Button)
        nameDoctorRapportET = findViewById(R.id.Name_Doctor_rapport)


        nameDoctorRapportET!!.isFocusable = false

        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                nameDoctorRapportET!!.setText(userItem.prenom + " " + userItem.nom)
            }

            override fun failure() {
            }
        })
        addRapport!!.setOnClickListener {
            if (TextRapport!!.text.isEmpty()) {
                var v = View.inflate(this, R.layout.fragment_dialog, null)
                var builder = AlertDialog.Builder(this)
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                    dialog.dismiss()
                }
            } else {
//                var v = View.inflate(this, R.layout.fragment_dialog, null)
//                var builder = AlertDialog.Builder(this)
//                builder.setView(v)
//
//                var dialog = builder.create()
//                dialog.show()
//                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//
//                dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
//                    dialog.dismiss()
//                }


                userDao.retrieveCurrentDataUser(object : UserCallback {
                    override fun onSuccess(userItem: UserItem) {
                        rapports.fullName = nameDoctorRapportET!!.text.toString()
                        rapports.textRapport = TextRapport!!.text.toString()
                        var id = userItem.id.toString()
                        userDao.insertRapport(rapports, userItem, id, object : ResponseCallback {
                            override fun success(medicament: String) {

                            }

                            override fun success() {
                                var toast = Toast.makeText(
                                    this@AddRapportDoctorActivity,
                                    "rapport ajouter avec succee",
                                    Toast.LENGTH_SHORT
                                )
                                toast.show()

                            }

                            override fun failure() {

                            }
                        })
                    }
                    override fun failure() {
                    }
                })
            }

        }
    }

}