package com.example.stagepfe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class TapTheCodeActivity : AppCompatActivity() {
    var SecondIconBack: ImageView? = null
    var NextButtonCode: Button? = null
    var CaseOne: EditText? = null
    var CaseTwo: EditText? = null
    var CaseThree: EditText? = null
    var CaseFour: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tap_the_code)
        init()

        SecondIconBack!!.setOnClickListener{
            var intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
        NextButtonCode!!.setOnClickListener{
            if ( CaseOne!!.text.isEmpty() || CaseTwo!!.text.isEmpty() || CaseThree!!.text.isEmpty() || CaseFour!!.text.isEmpty()) {
                var toast = Toast.makeText(
                    applicationContext,
                    "le champ est obligatoire",
                    Toast.LENGTH_SHORT
                )
                toast.show()


            } else {

                var intent = Intent(this, NewPasswrodActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }

    private fun init() {
        SecondIconBack = findViewById(R.id.SecondIconReturnBack)
        NextButtonCode = findViewById(R.id.NextTapTheCode)
        CaseOne= findViewById(R.id. CaseOne)
        CaseTwo= findViewById(R.id.CaseTwo)
        CaseThree= findViewById(R.id.CaseThree)
        CaseFour= findViewById(R.id. CaseFour)
    }
}