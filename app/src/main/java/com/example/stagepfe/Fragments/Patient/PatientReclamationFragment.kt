package com.example.stagepfe.Fragments.Patient

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.stagepfe.Activity.Doctors.AccountDoctorActivity
import com.example.stagepfe.Activity.Patients.BottomBarPatientActivity
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Reclamation
import com.example.stagepfe.entite.UserItem


class PatientReclamationFragment : Fragment() {
    private var namePatientET: EditText? = null
    private var phonePatientET: EditText? = null
    private var descriptionReclamationET: EditText? = null
    private var sendButton: Button? = null
    var userDao = UserDao()
    var reclamation = Reclamation()
    var userItem = UserItem()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_patient_reclamation, container, false)
        initView(view)
        return  view

    }

    private fun initView(view: View) {
        namePatientET = view.findViewById(R.id.Name_Patien_Reclamation)
        phonePatientET = view.findViewById(R.id.Phone_Patient_Reclamation)
        descriptionReclamationET = view.findViewById(R.id.Description_Reclamation_Patient)
        sendButton = view.findViewById(R.id.Send_Reclamtion_Patient)

        namePatientET!!.isFocusable = false
        phonePatientET!!.isFocusable = false

         userDao.retrieveCurrentDataUser( object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                namePatientET!!.setText(userItem.prenom + " " + userItem.nom)
                phonePatientET!!.setText(userItem.phonenumber)
            }

            override fun failure() {

            }
        })

        sendButton!!.setOnClickListener {
            if (descriptionReclamationET!!.text.isEmpty()) {
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
                var v = View.inflate(requireContext(), R.layout.fragment_dialog, null)
                var builder = AlertDialog.Builder(requireContext())
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                    dialog.dismiss()
                }
            }

            reclamation.fullName = namePatientET!!.text.toString()
            reclamation.description = descriptionReclamationET!!.text.toString()
            reclamation.phoneNumber = phonePatientET!!.text.toString()
            userDao.insertReclamation(reclamation)
            requireActivity().run {
                var intent = Intent(this, BottomBarPatientActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }


}