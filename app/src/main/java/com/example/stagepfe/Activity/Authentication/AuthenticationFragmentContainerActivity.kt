package com.example.stagepfe.Activity.Authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stagepfe.Activity.AgentLabo.AccueilAgentLaboActivity
import com.example.stagepfe.Activity.Doctors.AccountDoctorActivity
import com.example.stagepfe.Fragments.Authentication.ConnexionFragment
import com.example.stagepfe.Activity.Patients.BottomBarPatientActivity
import com.example.stagepfe.Activity.Pharmacien.AccueilPharmacienActivity
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem
import com.google.firebase.auth.FirebaseAuth

class AuthenticationFragmentContainerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticatio_fragment_container)


        var firstFragment = ConnexionFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentLayout, firstFragment).commit()



    }

    override fun onResume() {
        super.onResume()
        var userDao = UserDao()
        if(FirebaseAuth.getInstance().currentUser!=null){
            userDao.retrieveCurrentDataUser(object : UserCallback {
                    override fun onSuccess(userItem: UserItem) {
                        if (userItem.role!!.contains("Patient") && userItem.role!!.size==1){
                            var intent = Intent(this@AuthenticationFragmentContainerActivity, BottomBarPatientActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else
                            if(userItem.role!!.containsAll(listOf("Médecin","Patient"))) {
                                    var intent = Intent(
                                        this@AuthenticationFragmentContainerActivity,
                                        AccountDoctorActivity::class.java
                                    )
                                    startActivity(intent)
                                    finish()

                                }else if (userItem.role!!.containsAll(listOf("Labo","Patient"))){
                                var intent = Intent(
                                    this@AuthenticationFragmentContainerActivity,
                                    AccueilAgentLaboActivity::class.java
                                )
                                startActivity(intent)
                                finish()
                        }else if (userItem.role!!.containsAll(listOf("Pharmacien","Patient"))){
                                var intent = Intent(
                                    this@AuthenticationFragmentContainerActivity,
                                    AccueilPharmacienActivity::class.java
                                )
                                startActivity(intent)
                                finish()
                            }
                    }

                    override fun failure() {

                    }

                })


        }
    }

}