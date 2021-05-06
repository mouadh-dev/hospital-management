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
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem

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



        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                var nom = userItem.nom
                var prenom = userItem.prenom
                var datenaiss = userItem.datenaiss
                var phoneNumber = userItem.phonenumber
                var adress = userItem.adresse
                var bio = userItem.bio
                var groupesanguin = userItem.groupesanguin.toString()
                var id = userItem.id.toString()
                var mail = userItem.mail.toString()
                var matricule = userItem.matricule.toString()
                var numCIN = userItem.numCIN.toString()
                var rendezVous = userItem.rendezVous
                var role = userItem.role
                var sexe = userItem.sexe.toString()
                var speciality = userItem.speciality
                var password = userItem.password
                var confirmpassword = userItem.confirmpassword
                var maladi = userItem.maladi.toString()
                var medicament = userItem.medicament
                var ordonance = userItem.ordonance
                var rapport = userItem.rapport
                ////

                modifyPassword!!.setOnClickListener {
                    if (!(actualPassword!!.text.toString().equals(confirmpassword.toString()))){
                        text = "le mot de passe est incorrecte"
                        dialog(text)
                        newPasswordDoctorET!!.text.clear()
                        confirmNewPasswordDoctorET!!.text.clear()
                        actualPassword!!.text.clear()
                    }else
                        if (newPasswordDoctorET!!.text.isEmpty() || confirmNewPasswordDoctorET!!.text.isEmpty()) {
                        dialog(text)
                    } else {
                        user.nom = nom
                        user.prenom = prenom
                        user.datenaiss = datenaiss
                        user.phonenumber = phoneNumber
                        user.adresse = adress
                        user.bio = bio
                        user.id = id
                        user.mail = mail
                        user.matricule = matricule
                        user.numCIN = numCIN
                        user.rendezVous = rendezVous
                        user.role = role
                        user.sexe = sexe
                        user.speciality = speciality
                        user.password = newPasswordDoctorET!!.text.toString()
                        user.confirmpassword = confirmNewPasswordDoctorET!!.text.toString()
                        user.maladi = maladi
                        user.medicament = medicament
                        user.ordonance = ordonance
                        user.rapport = rapport
                        user.groupesanguin = groupesanguin

                        text = "Modification terminée avec succes"

                        userDao.updateUser(user.id.toString(), user, object : UserCallback {
                            override fun onSuccess(user: UserItem) {
                                var toast = Toast.makeText(this@ModifyPasswordActivity,"Modification terminée avec succes",Toast.LENGTH_SHORT)
                                toast.show()
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
//        actualPassword!!.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                userDao.retrieveCurrentDataUser(object : UserCallback {
//                    override fun onSuccess(userItem: UserItem) {
//                        if (!actualPassword!!.equals(userItem.confirmpassword.toString())) {
//                            modifyPassword!!.isEnabled = false
//                            modifyPassword!!.setBackgroundResource(R.drawable.gray_button)
//                            newPasswordDoctorET!!.error = "le mot de passe est incorrecte "
//                            newPasswordDoctorET!!.isFocusable = false
//                            confirmNewPasswordDoctorET!!.isFocusable = false
//                        }else{
//                            modifyPassword!!.setBackgroundResource(R.drawable.button_style_smaller)
//                            newPasswordDoctorET!!.isFocusable = true
//                            confirmNewPasswordDoctorET!!.isFocusable = true
//                        }
//                    }
//
//                    override fun failure() {
//
//                    }
//                })
//            }
//        })
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