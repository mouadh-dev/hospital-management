package com.example.stagepfe.Doctor.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Doctor.ModelMessagePatient
import com.example.stagepfe.Doctor.MyAdapterMessagePatient
import com.example.stagepfe.R


class DoctorMessageFragment : Fragment() {
    var listviewPat: ListView? = null
    var listPat = mutableListOf<ModelMessagePatient>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
       var view=  inflater.inflate(R.layout.fragment_doctor_message, container, false)
        listviewPat = view.findViewById<ListView>(R.id.listPatient)
        listPat.add(ModelMessagePatient("Mouadh" ,"merci docteur","12:44" ,R.drawable.logopatient))
        listPat.add(ModelMessagePatient("Mohamed" ,"je veut annuler mon RDV ","21:03" ,R.drawable.logopatient))
        listPat.add(ModelMessagePatient("Mouadh" ,"merci docteur","12:94" ,R.drawable.logopatient))
        listviewPat!!.adapter=MyAdapterMessagePatient(requireContext(),R.layout.message_patients_list,listPat)
        initView(view)
        return view

    }

    private fun initView(view: View) {

    }
}
