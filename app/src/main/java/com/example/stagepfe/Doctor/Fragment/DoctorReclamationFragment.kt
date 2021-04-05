package com.example.stagepfe.Doctor.Fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.stagepfe.R



class DoctorReclamationFragment : Fragment() {
    private var fullNameReclamationET: EditText? = null
    private var phoneNumberReclamationET: EditText? = null
    private var descriptionReclamationET: EditText? = null
    private var sendButton: Button? = null

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

            }
        }
    }

}