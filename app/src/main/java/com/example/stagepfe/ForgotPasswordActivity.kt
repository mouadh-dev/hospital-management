//package com.example.stagepfe
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ImageView
//import android.widget.Toast
//
//class ForgotPasswordActivity : AppCompatActivity() {
//     var BackIcon: ImageView? = null
//    var NextButton: Button? = null
//    var MailForgotPassword: EditText? =null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_forgot_password)
//        init()
//
//        BackIcon!!.setOnClickListener{
//            var intent= Intent(this, ConnexionActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//        NextButton!!.setOnClickListener{
//            if ( MailForgotPassword!!.text.isEmpty()) {
//                var toast = Toast.makeText(
//                    applicationContext,
//                    "le champ est obligatoire",
//                    Toast.LENGTH_SHORT
//                )
//                toast.show()
//
//
//            } else {
//
//                var intent = Intent(this, TapTheCodeActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//
//        }
//    }
//
//    private fun init() {
//        BackIcon = findViewById<ImageView>(R.id.IconReturnBack)
//        NextButton = findViewById<Button>(R.id.NextForgotPassword)
//        MailForgotPassword = findViewById(R.id.MailForgotPassword)
//    }
//}
