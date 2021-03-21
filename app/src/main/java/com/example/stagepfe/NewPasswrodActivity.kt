//package com.example.stagepfe
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//
//class NewPasswrodActivity : AppCompatActivity() {
//    var FinishButton: Button? = null
//    var NewPassword: EditText? = null
//    var  ConfirmNewPassword: EditText? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_new_passwrod)
//        initView()
//
//        FinishButton!!.setOnClickListener{
//            if ( NewPassword!!.text.isEmpty() || ConfirmNewPassword!!.text.isEmpty()) {
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
//                var intent = Intent(this, ConnexionActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//
//        }
//    }
//
//    private fun initView() {
//        FinishButton = findViewById<Button>(R.id.FinishButtonNewPassword)
//       NewPassword = findViewById(R.id.NewPassword)
//     ConfirmNewPassword = findViewById(R.id.ConfirmNewPassword)
//
//    }
//}