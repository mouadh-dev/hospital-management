package com.example.stagepfe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class PatientInscriptionActivity : AppCompatActivity() {
    var ReturnButton: Button? = null
    var ConditionSpinner: Spinner? = null
    var FinishButton: Button? = null
    var Medicament: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_inscription)
        init()

        ReturnButton!!.setOnClickListener {
            val intent = Intent(this, ChoosePositionActivity::class.java)
            startActivity(intent)
            finish()
        }
        FinishButton!!.setOnClickListener {
            if (Medicament!!.text.isEmpty()) {
                var toast = Toast.makeText(
                    applicationContext,
                    "le champ est obligatoire",
                    Toast.LENGTH_SHORT
                )
                toast.show()

            } else {
                val intent = Intent(this, ConnexionActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
        ArrayAdapter.createFromResource(
            this,
            R.array.User_Position,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            ConditionSpinner!!.adapter = adapter
        }

    }

    private fun init() {
        ConditionSpinner = findViewById(R.id.ConditionSpinner)
        ReturnButton = findViewById<Button>(R.id.ReturnButtonPatientInscription)
        FinishButton = findViewById<Button>(R.id.FinishButtonPatientInscription)
        Medicament = findViewById(R.id.Medicament)
    }
}