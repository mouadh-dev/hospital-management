package com.example.stagepfe.Activity.Doctors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem

class AddAnalyseOrdonnanceActivity : AppCompatActivity() {
    private var TextAnalyse: EditText? = null
    private var addAnalyse: Button? = null
    private var nameDoctorAnalyseET: EditText? = null
    var nameDoctorAnaylse:String? = null
    var userItem = UserItem()
    var idDoctor:String? = null
    var userDao = UserDao()
    var speciality:String? =  null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_analyse_ordonnance)
        initView()
    }

    private fun initView() {
        TextAnalyse = findViewById(R.id.text_Analyse_Et)
        addAnalyse = findViewById(R.id.Add_Analyse_Button)
        nameDoctorAnalyseET = findViewById(R.id.Name_Doctor_analyse)

       var patient = intent.getStringExtra("namePatentToOrdonance")
        nameDoctorAnalyseET!!.setText(patient)
        nameDoctorAnalyseET!!.isFocusable = false

        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                nameDoctorAnaylse = userItem.prenom + " " + userItem.nom
                idDoctor = userItem.id.toString()
                speciality = userItem.speciality.toString()
            }
            override fun failure() {
            }
        })
    }
}