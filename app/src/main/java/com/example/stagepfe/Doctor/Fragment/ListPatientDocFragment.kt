package com.example.stagepfe.Doctor.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Doctor.ModelPatientList
import com.example.stagepfe.Doctor.MyAdapterListPatient
import com.example.stagepfe.R


class ListPatientDocFragment : Fragment() {
    var listviewPatientDoc: ListView? = null
    var listPatientDoc = mutableListOf<ModelPatientList>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view=inflater.inflate(R.layout.fragment_list_patient_doc, container, false)
    initView(view)
        return view

    }

    private fun initView(view: View) {
        listviewPatientDoc = view.findViewById<ListView>(R.id.listPatientDocteur)
        listPatientDoc.add(ModelPatientList("Mohamed Rouahi", "Voir Plus", R.drawable.logopatient))
        listviewPatientDoc!!.adapter= MyAdapterListPatient(requireContext(),R.layout.list_patient, listPatientDoc)
    }


}