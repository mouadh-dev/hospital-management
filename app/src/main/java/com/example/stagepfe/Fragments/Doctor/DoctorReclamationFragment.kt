package com.example.stagepfe.Fragments.Doctor

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.Activity.Doctors.AccountDoctorActivity
import com.example.stagepfe.Activity.Doctors.DoctorProfilActivity
import com.example.stagepfe.Dao.ResponseCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Reclamation
import com.example.stagepfe.entite.UserItem


class DoctorReclamationFragment : Fragment() {
    private var fullNameReclamationET: EditText? = null
    private var phoneNumberReclamationET: EditText? = null
    private var descriptionReclamationET: EditText? = null
    private var sendButton: Button? = null
    var userDao = UserDao()
    var reclamation = Reclamation()
    var userItem = UserItem()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_doctor_reclamation, container, false)
        initView(view)
        return  view

    }

    private fun initView(view: View) {
        fullNameReclamationET = view.findViewById(R.id.ReclamationFullName)
        phoneNumberReclamationET = view.findViewById(R.id.ReclamationPhoneNumber)
        descriptionReclamationET = view.findViewById(R.id.DescriptionReclamation)
        sendButton = view.findViewById<Button>(R.id.SendbuttonReclamtion)

        fullNameReclamationET!!.isFocusable = false
        phoneNumberReclamationET!!.isFocusable = false


        userDao.retrieveDataUser(requireActivity(), userItem, object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                fullNameReclamationET!!.setText(userItem.prenom + " " + userItem.nom)
                phoneNumberReclamationET!!.setText(userItem.phonenumber)
            }

            override fun failure() {

            }
        })


        sendButton!!.setOnClickListener {
            if (fullNameReclamationET!!.text.isEmpty() || phoneNumberReclamationET!!.text.isEmpty()
                || descriptionReclamationET!!.text.isEmpty()) {
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



                        reclamation.fullName = fullNameReclamationET!!.text.toString()
                        reclamation.description = descriptionReclamationET!!.text.toString()
                        reclamation.phoneNumber = phoneNumberReclamationET!!.text.toString()
                        userDao.insertReclamation(reclamation)
                        requireActivity().run {
                                    var intent = Intent(this, AccountDoctorActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
//
//


                    }
            }
            }
        }


}