package com.example.stagepfe.Activity.Doctors

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.Adapters.Doctor.MyAdapterUpdateDoctor
import com.example.stagepfe.Models.Doctors.ModelUpdateDoctor
import com.example.stagepfe.R

class UpdateProfileDoctorActivity : AppCompatActivity() {
    var listUpdate = mutableListOf<ModelUpdateDoctor>()
    var listViewUpdate: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile_doctor)
        initView()
    }

    private fun initView() {
        listViewUpdate = findViewById(R.id.updateDoctorList)

        listUpdate.add(ModelUpdateDoctor("Changer les informations"))
        listUpdate.add(ModelUpdateDoctor("Changer le mot de passe"))
        initAdapter()

        listViewUpdate!!.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                var intent = Intent(this, ChangeInformationDoctorActivity::class.java)
                startActivity(intent)
            } else if (position == 1) {
                var intent = Intent(this, ModifyPasswordActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun initAdapter() {
        listViewUpdate!!.adapter =
            MyAdapterUpdateDoctor(this, R.layout.list_update_doctor, listUpdate)
    }
}