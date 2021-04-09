package com.example.stagepfe.Fragments.Patient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.stagepfe.Models.Patient.ModelMessagePatient
import com.example.stagepfe.Adapters.Patients.MyAdapterMessagePatient
import com.example.stagepfe.R


class MessagePatientFragment : Fragment() {
    var listMessagePatient: ListView? = null
    var list = mutableListOf<ModelMessagePatient>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_message_patient, container, false)

        listMessagePatient = view.findViewById<ListView>(R.id.Message__Patient)

        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
        list.add(ModelMessagePatient("Mouadh", "merci docteur", "12:44", R.drawable.doctor_ic))
        listMessagePatient!!.adapter=
            MyAdapterMessagePatient(requireContext(), R.layout.message_patient, list)
        return view
    }


}