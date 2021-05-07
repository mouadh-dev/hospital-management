package com.example.stagepfe.Activity.AgentLabo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.stagepfe.Activity.Patients.ChangeInformationPatientActivity
import com.example.stagepfe.Activity.Patients.ModifyPasswordPatientActivity
import com.example.stagepfe.Adapters.AgentLabo.MyAdapterAgentUpdate
import com.example.stagepfe.Adapters.Patients.MyAdapterPatientUpdate
import com.example.stagepfe.Models.AgentLabo.ModelUpdateAgent
import com.example.stagepfe.Models.Patient.ModelUpdatePatient
import com.example.stagepfe.R

class UpdateProfileAgentActivity : AppCompatActivity() {
    var listUpdateAgent = mutableListOf<ModelUpdateAgent>()
    var listViewUpdateAgent: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile_agent)
        initView()
    }

    private fun initView() {

        listViewUpdateAgent = findViewById(R.id.updateAgentList)

        listUpdateAgent.add(ModelUpdateAgent("Changer les informations"))
        listUpdateAgent.add(ModelUpdateAgent("Changer le mot de passe"))
        initAdapter()

        listViewUpdateAgent!!.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                var intent = Intent(this, ChangeInformationAgentActivity::class.java)
                startActivity(intent)
            } else if (position == 1) {
                var intent = Intent(this, ModifyPasswordAgentActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun initAdapter() {
        listViewUpdateAgent!!.adapter =
            MyAdapterAgentUpdate(this, R.layout.list_update_agent, listUpdateAgent)
    }
}