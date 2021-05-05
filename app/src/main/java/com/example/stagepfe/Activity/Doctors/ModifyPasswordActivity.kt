package com.example.stagepfe.Activity.Doctors

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.R

class ModifyPasswordActivity : AppCompatActivity() {
    private var newPasswordDoctorET: EditText? = null
    private var confirmNewPasswordDoctorET: EditText? = null
    private var showNewPasswordIV: ImageView? = null
    private var showNewPassworConfirmdIV: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_password)
        initView()
    }

    private fun initView() {
        newPasswordDoctorET = findViewById(R.id.NewPasswordDoctor)
        confirmNewPasswordDoctorET = findViewById(R.id.ConfirmNewPasswordDoctor)
        showNewPasswordIV = findViewById(R.id.Eye_Show_newPassword)
        showNewPassworConfirmdIV = findViewById(R.id.Eye_Show_ConfirmnewPassword_Doctor)

        newPasswordDoctorET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (newPasswordDoctorET!!.length() <= 8) {
//                    saveProfilButton!!.isEnabled = false
//                    saveProfilButton!!.setBackgroundResource(R.drawable.gray_button)
                    newPasswordDoctorET!!.error = "le mot de passe est faible "
                } else {

//                    saveProfilButton!!.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }
        })
        showNewPasswordIV!!.setOnClickListener {
            if (newPasswordDoctorET!!.getTransformationMethod()
                    .equals(PasswordTransformationMethod.getInstance())
            ) {
                newPasswordDoctorET!!.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                newPasswordDoctorET!!.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }
////////////////////////////////////////////////////////////////////////////////////////////////////
        confirmNewPasswordDoctorET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (notequal()) {
//                    saveProfilButton!!.setBackgroundResource(R.drawable.gray_button)
                    confirmNewPasswordDoctorET!!.error = "le mot de passe ne correspond pas"
                } else {
//                    saveProfilButton!!.isEnabled = true
//                    saveProfilButton!!.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }


        })
        showNewPassworConfirmdIV!!.setOnClickListener {
            if (confirmNewPasswordDoctorET!!.getTransformationMethod()
                    .equals(PasswordTransformationMethod.getInstance())
            ) {
                confirmNewPasswordDoctorET!!.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                confirmNewPasswordDoctorET!!.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }
    }

    private fun notequal(): Boolean {
        return confirmNewPasswordDoctorET!!.text.toString() != newPasswordDoctorET!!.text.toString()
    }


}