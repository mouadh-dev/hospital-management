package com.example.stagepfe.Activity.Patients

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.stagepfe.Activity.Doctors.ChangeInformationDoctorActivity
import com.example.stagepfe.Activity.Doctors.ModifyPasswordActivity
import com.example.stagepfe.Adapters.Doctor.MyAdapterUpdateDoctor
import com.example.stagepfe.Adapters.Patients.MyAdapterPatientUpdate
import com.example.stagepfe.Models.Doctors.ModelUpdateDoctor
import com.example.stagepfe.Models.Patient.ModelUpdatePatient
import com.example.stagepfe.R

class UpdateProfilePatientActivity : AppCompatActivity() {
    var listUpdate = mutableListOf<ModelUpdatePatient>()
    var listViewUpdate: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile_patient)
        initView()
    }

    private fun initView() {

        listViewUpdate = findViewById(R.id.updatePatientList)

        listUpdate.add(ModelUpdatePatient("Changer les informations"))
        listUpdate.add(ModelUpdatePatient("Changer le mot de passe"))
        initAdapter()

        listViewUpdate!!.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                var intent = Intent(this, ChangeInformationPatientActivity::class.java)
                startActivity(intent)
            } else if (position == 1) {
                var intent = Intent(this, ModifyPasswordPatientActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun initAdapter() {
        listViewUpdate!!.adapter =
            MyAdapterPatientUpdate(this, R.layout.list_update_patient, listUpdate)
    }
}