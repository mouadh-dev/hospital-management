package com.example.stagepfe.Activity.AgentLabo

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.*
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem

class ModifyPasswordAgentActivity : AppCompatActivity() {
    private var newPasswordAgentET: EditText? = null
    private var confirmNewPasswordAgentET: EditText? = null
    private var actualPasswordAgent: EditText? = null
    private var showNewPasswordAgentIV: ImageView? = null
    private var showNewPasswordAgentConfirmdIV: ImageView? = null
    var modifyPasswordAgent: Button? = null
    var userDao = UserDao()
    var text: String = ""
    var user = UserItem()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_password_agent)
        initView()
    }

    private fun initView() {
        newPasswordAgentET = findViewById(R.id.NewPasswordAgentET)
        confirmNewPasswordAgentET = findViewById(R.id.ConfirmNewPasswordAgentET)
        showNewPasswordAgentIV = findViewById(R.id.Eye_Show_newPasswordAgent )
        showNewPasswordAgentConfirmdIV = findViewById(R.id.Eye_Show_ConfirmnewPassword_AgentIV)
        modifyPasswordAgent = findViewById(R.id.modify_password_buttonAgent )
        actualPasswordAgent = findViewById(R.id.actual_passwordAgent )

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

                modifyPasswordAgent!!.setOnClickListener {
                    if (!(actualPasswordAgent!!.text.toString().equals(confirmpassword.toString()))){
                        text = "le mot de passe est incorrecte"
                        dialog(text)
                        newPasswordAgentET!!.text.clear()
                        confirmNewPasswordAgentET!!.text.clear()
                        actualPasswordAgent!!.text.clear()
                    }else
                        if (newPasswordAgentET!!.text.isEmpty() || confirmNewPasswordAgentET!!.text.isEmpty()) {
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
                            user.password = newPasswordAgentET!!.text.toString()
                            user.confirmpassword = confirmNewPasswordAgentET!!.text.toString()
                            user.maladi = maladi
                            user.medicament = medicament
                            user.ordonance = ordonance
                            user.rapport = rapport
                            user.groupesanguin = groupesanguin

                            text = "Modification terminée avec succes"

                            userDao.updateUser(user.id.toString(), user, object : UserCallback {
                                override fun onSuccess(user: UserItem) {
                                    var toast = Toast.makeText(this@ModifyPasswordAgentActivity,"Modification terminée avec succes",
                                        Toast.LENGTH_SHORT)
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
        newPasswordAgentET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (newPasswordAgentET!!.length() <= 8) {
                    modifyPasswordAgent!!.isEnabled = false
                    modifyPasswordAgent!!.setBackgroundResource(R.drawable.gray_button)
                    newPasswordAgentET!!.error = "le mot de passe est faible "
                } else {

                    modifyPasswordAgent!!.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }
        })
        showNewPasswordAgentIV!!.setOnClickListener {
            if (newPasswordAgentET!!.getTransformationMethod()
                    .equals(PasswordTransformationMethod.getInstance())
            ) {
                newPasswordAgentET!!.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                newPasswordAgentET!!.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }
////////////////////////////////////////////////////////////////////////////////////////////////////
        confirmNewPasswordAgentET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (notequal()) {
                    modifyPasswordAgent!!.setBackgroundResource(R.drawable.gray_button)
                    confirmNewPasswordAgentET!!.error = "le mot de passe ne correspond pas"
                } else {
                    modifyPasswordAgent!!.isEnabled = true
                    modifyPasswordAgent!!.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }

        })
        showNewPasswordAgentConfirmdIV!!.setOnClickListener {
            if (confirmNewPasswordAgentET!!.getTransformationMethod()
                    .equals(PasswordTransformationMethod.getInstance())
            ) {
                confirmNewPasswordAgentET!!.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                confirmNewPasswordAgentET!!.transformationMethod =
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
        return confirmNewPasswordAgentET!!.text.toString() != newPasswordAgentET!!.text.toString()
    }
}