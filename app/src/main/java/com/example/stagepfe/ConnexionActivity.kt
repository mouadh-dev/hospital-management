//package com.example.stagepfe
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Button
//import android.widget.TextView
//
//class ConnexionActivity : AppCompatActivity() {
//    var InscriptionButton: Button? = null
//    var ForgotPassWord: TextView? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_connexion)
//
//        init()
//
//        InscriptionButton!!.setOnClickListener {
//            val intent = Intent(this, InscriptionFirstPageActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//        ForgotPassWord!!.setOnClickListener{
//            var intent = Intent(this , ForgotPasswordActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }
//
//    private fun init() {
//        ForgotPassWord = findViewById<TextView>(R.id.Motdepasseoubliee)
//        InscriptionButton = findViewById<Button>(R.id.InscriptionButton)
//    }
//}
//
//
//
