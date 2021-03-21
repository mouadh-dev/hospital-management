package com.example.stagepfe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stagepfe.Fragment.ConnexionFragment
import com.example.stagepfe.Fragment.ForgotPasswordFragment

class ContainerFragmentPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container_fragment_password)

        var PasswordFragment = ForgotPasswordFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerForgotPassword, PasswordFragment).commit()
    }
}