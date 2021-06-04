package com.example.stagepfe.Fragments.Pharmacien

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.stagepfe.Activity.AgentLabo.AccueilAgentLaboActivity
import com.example.stagepfe.Activity.Pharmacien.AccueilPharmacienActivity
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Reclamation
import com.example.stagepfe.entite.UserItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class PharmacienReclamationFragment : Fragment() {
    private var fullNameReclamationPharmacienET: EditText? = null
    private var phoneNumberReclamationPharmacienET: EditText? = null
    private var descriptionReclamationPharmacienET: EditText? = null
    private var sendButtonPharmacien: Button? = null
    private var idReclameur: String?= null
    var userDao = UserDao()
    var reclamation = Reclamation()
    var userItem = UserItem()
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDateTime = LocalDateTime.now()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view=  inflater.inflate(R.layout.fragment_pharmacien_reclamation, container, false)
        initView(view)
        return  view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initView(view: View) {

        fullNameReclamationPharmacienET = view.findViewById(R.id.ReclamationFullNamePharmacien)
        phoneNumberReclamationPharmacienET = view.findViewById(R.id.ReclamationPhoneNumberPharmacien)
        descriptionReclamationPharmacienET = view.findViewById(R.id.DescriptionReclamationPharmacien)
        sendButtonPharmacien = view.findViewById<Button>(R.id.SendbuttonReclamtionPharmacien)

        fullNameReclamationPharmacienET!!.isFocusable = false
        phoneNumberReclamationPharmacienET!!.isFocusable = false

        userDao.retrieveCurrentDataUser( object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                fullNameReclamationPharmacienET!!.setText(userItem.prenom + " " + userItem.nom)
                phoneNumberReclamationPharmacienET!!.setText(userItem.phonenumber)
                idReclameur = userItem.id.toString()
            }

            override fun failure() {
            }
        })
        sendButtonPharmacien!!.setOnClickListener {
            if (descriptionReclamationPharmacienET!!.text.isEmpty()) {
                var v = View.inflate(requireContext(), R.layout.fragment_dialog, null)
                var builder = AlertDialog.Builder(requireContext())
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                    dialog.dismiss()
                }
            }else{
                var v = View.inflate(
                    requireContext(),
                    R.layout.fragment_dialog,
                    null
                )
                var builder = AlertDialog.Builder(requireContext())
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.findViewById<TextView>(R.id.TitleDialog)
                    .setText("votre réclamation a été bien envoyée ")
                dialog.findViewById<ImageView>(R.id.CheckDialog)
                    .setBackgroundResource(R.drawable.ellipse_green)
                dialog.findViewById<ImageView>(R.id.CheckDialog).setImageResource(R.drawable.check)
                dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE

                dialog.findViewById<Button>(R.id.btn_confirm)
                    .setOnClickListener {
                        dialog.dismiss()



                        reclamation.fullName = fullNameReclamationPharmacienET!!.text.toString()
                        reclamation.description = descriptionReclamationPharmacienET!!.text.toString()
                        reclamation.phoneNumber = phoneNumberReclamationPharmacienET!!.text.toString()
                        reclamation.idReclameur = idReclameur
                        reclamation.timeReclamation = currentDateTime.format(DateTimeFormatter.ISO_TIME)
                        reclamation.dateReclamation = currentDateTime.format(DateTimeFormatter.ISO_DATE)
                        userDao.insertReclamation(reclamation)
                        requireActivity().run {
                            var intent = Intent(this,AccueilPharmacienActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }
            }
        }
    }
}