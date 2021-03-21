//package com.example.stagepfe
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.content.Intent
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//
//
//class InscriptionFirstPageActivity : AppCompatActivity() {
//    //declaration des views
//    var ButtonReturn: Button? = null
//    var ButtonNext: Button? = null
//    var FirstName: EditText? = null
//    var LastName: EditText? = null
//    var Mail: EditText? = null
//    var Password: EditText? = null
//    var ConfirmPass: EditText? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_inscription_first_page)
//        initView()
//
//
//        ButtonReturn?.setOnClickListener {
//            val intent = Intent(this, ConnexionActivity::class.java)
//            startActivity(intent)
//            finish()
//
//        }
//
//        ButtonNext!!.setOnClickListener {
//            if (LastName!!.text.isEmpty() || FirstName!!.text.isEmpty() || Mail!!.text.isEmpty() || Password!!.text.isEmpty() || ConfirmPass!!.text.isEmpty()) {
//                var toast = Toast.makeText(
//                    applicationContext,
//                    "le champ est obligatoire",
//                    Toast.LENGTH_SHORT
//                )
//                toast.show()
//
//            } else {
//                if (ConfirmPass!!.text.equals(Password!!.text)) {
//                    var toast = Toast.makeText(applicationContext, " Mot de passe non confirmé", Toast.LENGTH_SHORT)
//                    toast.show()
//                } else {
//
//                    var intent = Intent(this, InscriptionSecondPageActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//            }
//        }
//    }
//    private fun initView() {
//
//        ButtonReturn = findViewById<Button>(R.id.ReturnbuttonFirstPage)
//        ButtonNext = findViewById<Button>(R.id.NextButtonFirstPage)
//        LastName= findViewById(R.id.InscriptionLastNameFirstPage)
//        FirstName= findViewById(R.id.InscriptionFirstNameFirstPage)
//        Mail= findViewById(R.id.InscriptionMailFirstPage)
//        Password= findViewById(R.id.InscriptionPasswordFirstPage)
//        ConfirmPass= findViewById(R.id.InscriptionConfirmPassFirstPage)
//    }
//
//
////    private fun validateInput():Boolean{
////        return InscriptionNom?.text.toString().trim().equals("")
////    }
//
//}
//
//
