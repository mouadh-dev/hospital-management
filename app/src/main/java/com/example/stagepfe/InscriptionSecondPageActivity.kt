package com.example.stagepfe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast

class InscriptionSecondPageActivity : AppCompatActivity() {
    var ButtonReturn: Button? = null
    var ButtonNext: Button? = null
    var  Adresse: EditText? = null
    var DateNaiss: EditText? = null
    var  PhoneNumber: EditText? = null
    var  BloodGroup: EditText? = null
    var Sexe: RadioGroup?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription_second_page)

        initView()

        ButtonReturn?.setOnClickListener{
            val intent = Intent(this , InscriptionFirstPageActivity:: class.java)
            startActivity(intent)
            finish()

        }
        ButtonNext!!.setOnClickListener {
            if ( Adresse!!.text.isEmpty() || DateNaiss!!.text.isEmpty() || PhoneNumber!!.text.isEmpty() || BloodGroup!!.text.isEmpty()) {
                var toast = Toast.makeText(
                    applicationContext,
                    "le champ est obligatoire",
                    Toast.LENGTH_SHORT
                )
                toast.show()


                } else {

                    var intent = Intent(this, InscriptionSecondPageActivity::class.java)
                    startActivity(intent)
                    finish()
                }

        }
    }
    private fun initView() {

        ButtonReturn = findViewById<Button>(R.id.ReurnbuttonSecondePage)
        ButtonNext = findViewById<Button>(R.id.NextButtonFirstPage)
        Adresse= findViewById(R.id.InscriptionAdresseSecondPage)
       DateNaiss= findViewById(R.id.InscriptionDateSecondPage)
       PhoneNumber= findViewById(R.id.InscriptionPhoneNumberSecondPage)
       BloodGroup= findViewById(R.id.InscriptionBloodSecondPage)
        Sexe= findViewById(R.id.InscriptionSexeSecondPage)
    }
}