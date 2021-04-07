package com.example.stagepfe.Patient.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.stagepfe.R


class PatientReclamationFragment : Fragment() {
    private var namePatientET: EditText? = null
    private var phonePatientET: EditText? = null
    private var descriptionReclamationET: EditText? = null
    private var sendButton: Button? = null

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


        sendButton!!.setOnClickListener {
            if (namePatientET!!.text.isEmpty() || phonePatientET!!.text.isEmpty()
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
        }

    }


}