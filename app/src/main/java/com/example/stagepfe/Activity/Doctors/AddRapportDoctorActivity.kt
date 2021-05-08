package com.example.stagepfe.Activity.Doctors

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
    var rapports = Rapports()
    var id:String? = null
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
                id = userItem.id.toString()
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
                        rapports.fullName = nameDoctorRapportET!!.text.toString()
                        rapports.textRapport = TextRapport!!.text.toString()

                        userDao.insertRapport(rapports, userItem, id!!, object : ResponseCallback {


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
            "votre rapport a été créé avec succès"
        dialog.findViewById<ImageView>(R.id.CheckDialog)
            .setBackgroundResource(R.drawable.ellipse_green)
        dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            dialog.dismiss()
            finish()
        }
    }

}