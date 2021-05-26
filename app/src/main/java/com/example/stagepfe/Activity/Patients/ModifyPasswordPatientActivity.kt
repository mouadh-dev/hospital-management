package com.example.stagepfe.Activity.Patients

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.*
import com.example.stagepfe.Dao.ResponseCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem

class ModifyPasswordPatientActivity : AppCompatActivity() {
    private var newPasswordPatientET: EditText? = null
    private var confirmNewPasswordPatientET: EditText? = null
    private var actualPassword: EditText? = null
    private var showNewPasswordIV: ImageView? = null
    private var showNewPassworConfirmdIV: ImageView? = null
    var modifyPassword: Button? = null
    var userDao = UserDao()
    var text: String = ""
    var user = UserItem()
    var moveBack: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_password_patient)
        initView()
    }
    private fun initView() {
        newPasswordPatientET = findViewById(R.id.NewPasswordPatientET)
        confirmNewPasswordPatientET = findViewById(R.id.ConfirmNewPasswordPatientET)
        showNewPasswordIV = findViewById(R.id.Eye_Show_newPasswordPatient)
        showNewPassworConfirmdIV = findViewById(R.id.Eye_Show_ConfirmnewPassword_PatientIV)
        modifyPassword = findViewById(R.id.modify_password_button)
        actualPassword = findViewById(R.id.actual_passwordPatient)
        moveBack = findViewById(R.id.back_modifyProfilePatient)

        /////////////////////////////////////////change password////////////////////////////////////////////
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                modifyPassword!!.setOnClickListener {
                    if (!(actualPassword!!.text.toString().equals(userItem.confirmpassword.toString()))) {
                        text = "le mot de passe est incorrecte"
                        dialog(text)
                        newPasswordPatientET!!.text.clear()
                        confirmNewPasswordPatientET!!.text.clear()
                        actualPassword!!.text.clear()
                    } else
                        if (newPasswordPatientET!!.text.isEmpty() || confirmNewPasswordPatientET!!.text.isEmpty()) {
                            dialog(text)
                        } else {
                            var newPassword = confirmNewPasswordPatientET!!.text.toString()
                            userDao.changePassword(newPassword,
                                userItem, object : ResponseCallback {
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
        newPasswordPatientET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (newPasswordPatientET!!.length() <= 8) {
                    modifyPassword!!.isEnabled = false
                    modifyPassword!!.setBackgroundResource(R.drawable.gray_button)
                    newPasswordPatientET!!.error = "le mot de passe est faible "
                } else {

                    modifyPassword!!.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }
        })
        showNewPasswordIV!!.setOnClickListener {
            if (newPasswordPatientET!!.getTransformationMethod()
                    .equals(PasswordTransformationMethod.getInstance())
            ) {
                newPasswordPatientET!!.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                newPasswordPatientET!!.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }
////////////////////////////////////////////////////////////////////////////////////////////////////
        confirmNewPasswordPatientET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (notequal()) {
                    modifyPassword!!.setBackgroundResource(R.drawable.gray_button)
                    confirmNewPasswordPatientET!!.error = "le mot de passe ne correspond pas"
                } else {
                    modifyPassword!!.isEnabled = true
                    modifyPassword!!.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }


        })
        showNewPassworConfirmdIV!!.setOnClickListener {
            if (confirmNewPasswordPatientET!!.getTransformationMethod()
                    .equals(PasswordTransformationMethod.getInstance())
            ) {
                confirmNewPasswordPatientET!!.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                confirmNewPasswordPatientET!!.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }

        moveBack!!.setOnClickListener {
            finish()
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
        return confirmNewPasswordPatientET!!.text.toString() != newPasswordPatientET!!.text.toString()
    }


}