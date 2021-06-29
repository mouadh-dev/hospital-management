package com.example.stagepfe.Activity.Administrateur

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.stagepfe.Activity.Authentication.AuthenticationFragmentContainerActivity
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Fragments.Administrateur.AccueilAdministrateurFragment
import com.example.stagepfe.Fragments.Administrateur.AddUserAdmin
import com.example.stagepfe.Fragments.Administrateur.ReclamationAdministrateurFragment
import com.example.stagepfe.Fragments.Administrateur.UtlisitaeursAdministrateurFragment
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem
import com.google.android.material.bottomnavigation.BottomNavigationView

class AccueilAdministrateurActivity : AppCompatActivity() {

    var navigationAdministrateur: BottomNavigationView? = null
    var reclamationAdministrateur: LinearLayout? = null
    var homeAdministrateur: LinearLayout? = null
    var demandesAdministrateur: LinearLayout? = null
    var utlisateurAdministrateur: LinearLayout? = null
    var userDao = UserDao()
    var imageProfilAdmin:ImageView? = null
    private var imageUri: Uri? = null
    var nameAdmin:TextView? = null
    var changeUser:ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil_administrateur)
        initView()
    }

    private fun initView() {
        navigationAdministrateur = findViewById(R.id.bottom_nav_Administrateur)
        reclamationAdministrateur = findViewById(R.id.reclamationAdministrateurLayout)
        homeAdministrateur = findViewById(R.id.AccueilAdministrateurLayout)
        demandesAdministrateur = findViewById(R.id.DemandesAdministrateurLayout)
        utlisateurAdministrateur = findViewById(R.id.UtlisateurLayout)
        imageProfilAdmin = findViewById(R.id.IVimageProfilAdmin)
        nameAdmin = findViewById(R.id.admin_Name)
        changeUser = findViewById(R.id.change_user_admin)

        navigationAdministrateur!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_reclamation_adm -> {
                    reclamationAdministrateur!!.visibility = View.VISIBLE
                    homeAdministrateur!!.visibility = View.GONE
                    demandesAdministrateur!!.visibility = View.GONE
                    utlisateurAdministrateur!!.visibility = View.GONE

                    var reclamationAdministrateur = ReclamationAdministrateurFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentAdministrateur, reclamationAdministrateur)
                        .commit()

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_demandes -> {

                    utlisateurAdministrateur!!.visibility = View.GONE
                    reclamationAdministrateur!!.visibility = View.GONE
                    homeAdministrateur!!.visibility = View.GONE
                    demandesAdministrateur!!.visibility = View.VISIBLE


                    var DemandesAdministrateur = AddUserAdmin()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentAdministrateur, DemandesAdministrateur)
                        .commit()



                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_utilisateurs -> {

                    utlisateurAdministrateur!!.visibility = View.VISIBLE
                    demandesAdministrateur!!.visibility = View.GONE
                    reclamationAdministrateur!!.visibility = View.GONE
                    homeAdministrateur!!.visibility = View.GONE


                    var UtlisatursAdministrateur = UtlisitaeursAdministrateurFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentAdministrateur, UtlisatursAdministrateur)
                        .commit()


                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_home_adm -> {
                    homeAdministrateur!!.visibility = View.VISIBLE
                    utlisateurAdministrateur!!.visibility = View.GONE
                    demandesAdministrateur!!.visibility = View.GONE
                    reclamationAdministrateur!!.visibility = View.GONE


                    var HomeAdministrateur = AccueilAdministrateurFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerFragmentAdministrateur, HomeAdministrateur)
                        .commit()

                }

            }
            true
        })
/////////////////////////////////////////////////////////////////////////////////////////////////////
        imageProfilAdmin!!.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 1000)
        }
        /////////////
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                nameAdmin!!.text = userItem.nom + " " + userItem.prenom
            }

            override fun failure() {
            }
        })
        /////////////////
        changeUser!!.setOnClickListener {
            dialog()
        }
    }

    private fun dialog() {
        var v = View.inflate(this, R.layout.dialogchangeuser, null)
        var builder = AlertDialog.Builder(this)
        builder.setView(v)

        var dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.findViewById<TextView>(R.id.sign_out_tv).setOnClickListener {
            signout()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1000) {
            imageUri = data?.data
           // imageProfilAdmin!!.setImageURI(imageUri)

            userDao.uploadImageToFirebase(
                userDao.getCurrentUserId(),
                imageUri!!)
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    private fun signout() {
        var userDao = UserDao()
        userDao.signOut(UserItem(),object : UserCallback {
            override fun onSuccess(userItem: UserItem) {

                var intent = Intent(this@AccueilAdministrateurActivity,
                    AuthenticationFragmentContainerActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)//makesure user cant go back
                startActivity(intent)
                finish()
            }

            override fun failure() {
                var toast= Toast.makeText(this@AccueilAdministrateurActivity,"probleme", Toast.LENGTH_SHORT)
                toast.show()
            }
        })
    }

    override fun onResume() {
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                if(userItem.profilPhotos != null) {
                    Glide
                        .with(this@AccueilAdministrateurActivity)
                        .load(userItem.profilPhotos)
                        .into(imageProfilAdmin!!)
                }
            }
            override fun failure() {
            }
        })
        super.onResume()
    }
}