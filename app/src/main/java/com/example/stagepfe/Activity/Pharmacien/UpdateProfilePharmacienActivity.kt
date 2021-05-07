package com.example.stagepfe.Activity.Pharmacien

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.stagepfe.Activity.Doctors.ChangeInformationDoctorActivity
import com.example.stagepfe.Activity.Doctors.ModifyPasswordActivity
import com.example.stagepfe.Adapters.Doctor.MyAdapterUpdateDoctor
import com.example.stagepfe.Adapters.Pharmacien.MyAdapterUpdatePharmacien
import com.example.stagepfe.Models.Doctors.ModelUpdateDoctor
import com.example.stagepfe.Models.Pharmacien.ModelUpdatePharmacien
import com.example.stagepfe.R

class UpdateProfilePharmacienActivity : AppCompatActivity() {
    var listUpdatePharmacien = mutableListOf<ModelUpdatePharmacien>()
    var listViewUpdatePharmacien: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile_pharmacien)
        initView()
    }

    private fun initView() {
        listViewUpdatePharmacien = findViewById(R.id.updatePharmacienList)

        listUpdatePharmacien.add(ModelUpdatePharmacien("Changer les informations"))
        listUpdatePharmacien.add(ModelUpdatePharmacien("Changer le mot de passe"))
        initAdapter()

        listViewUpdatePharmacien!!.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                var intent = Intent(this, ChangeInformationPharmacienActivity::class.java)
                startActivity(intent)
            } else if (position == 1) {
                var intent = Intent(this, ModifyPasswordPharmacienActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initAdapter() {
        listViewUpdatePharmacien!!.adapter =
            MyAdapterUpdatePharmacien(this, R.layout.list_update_pharmacien, listUpdatePharmacien)
    }
}