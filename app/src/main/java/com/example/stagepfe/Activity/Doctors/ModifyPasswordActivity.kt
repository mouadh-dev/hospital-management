package com.example.stagepfe.Activity.Doctors

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.Dao.ResponseCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem
import com.google.android.gms.tasks.Task

class ModifyPasswordActivity : AppCompatActivity() {
    private var newPasswordDoctorET: EditText? = null
    private var confirmNewPasswordDoctorET: EditText? = null
    private var actualPassword:EditText? = null
    private var showNewPasswordIV: ImageView? = null
    private var showNewPassworConfirmdIV: ImageView? = null
    var modifyPassword: Button? = null
    var userDao = UserDao()
    var text: String = ""
    var user = UserItem()

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
        modifyPassword = findViewById(R.id.modify_password_button)
        actualPassword = findViewById(R.id.actual_password)




/////////////////////////////////////////change password////////////////////////////////////////////
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
        modifyPassword!!.setOnClickListener {
            if (!(actualPassword!!.text.toString().equals(userItem.confirmpassword.toString()))) {
                text = "le mot de passe est incorrecte"
                dialog(text)
                newPasswordDoctorET!!.text.clear()
                confirmNewPasswordDoctorET!!.text.clear()
                actualPassword!!.text.clear()
            } else
                if (newPasswordDoctorET!!.text.isEmpty() || confirmNewPasswordDoctorET!!.text.isEmpty()) {
                    dialog(text)
                } else {
                    var newPassword = confirmNewPasswordDoctorET!!.text.toString()
                    userDao.changePassword(newPassword,
                        userItem,object : ResponseCallback {
                        override fun success(medicament: String) {

                        }

                        override fun success() {


                            finish()
                        }

                        override fun failure() {
                        }
                    })
                }
        }
            }

            override fun failure() {

            }
        })
////////////////////////////////////////////////////////////////////////////////////////////////////
        newPasswordDoctorET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (newPasswordDoctorET!!.length() <= 8) {
                    modifyPassword!!.isEnabled = false
                    modifyPassword!!.setBackgroundResource(R.drawable.gray_button)
                    newPasswordDoctorET!!.error = "le mot de passe est faible "
                } else {

                    modifyPassword!!.setBackgroundResource(R.drawable.button_style_smaller)

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
                    modifyPassword!!.setBackgroundResource(R.drawable.gray_button)
                    confirmNewPasswordDoctorET!!.error = "le mot de passe ne correspond pas"
                } else {
                    modifyPassword!!.isEnabled = true
                    modifyPassword!!.setBackgroundResource(R.drawable.button_style_smaller)

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
////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun dialog(text: String) {
        var v = View.inflate(this, R.layout.fragment_dialog, null)
        var builder = AlertDialog.Builder(this)
        builder.setView(v)

        var dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.findViewById<TextView>(R.id.TitleDialog).text = text
        dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE
    dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
        dialog.dismiss()
    }
    }

    private fun notequal(): Boolean {
        return confirmNewPasswordDoctorET!!.text.toString() != newPasswordDoctorET!!.text.toString()
    }


}