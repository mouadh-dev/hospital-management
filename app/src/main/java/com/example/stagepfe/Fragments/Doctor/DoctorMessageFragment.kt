package com.example.stagepfe.Fragments.Doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Models.Doctors.ModelDoctorMessage
import com.example.stagepfe.Adapters.Doctor.MyAdapterDoctorMessage
import com.example.stagepfe.R


class DoctorMessageFragment : Fragment() {
    var listviewPatient: ListView? = null
    var listPat = mutableListOf<ModelDoctorMessage>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
       var view=  inflater.inflate(R.layout.fragment_doctor_message, container, false)
        listviewPatient = view.findViewById<ListView>(R.id.listPatient)
        listPat.add(ModelDoctorMessage("Mouadh" ,"merci docteur","12:44" ,R.drawable.logopatient))
        listPat.add(ModelDoctorMessage("Mohamed" ,"je veut annuler mon RDV ","21:03" ,R.drawable.logopatient))
        listPat.add(ModelDoctorMessage("Mouadh" ,"merci docteur","12:94" ,R.drawable.logopatient))
        listPat.add(ModelDoctorMessage("Mouadh" ,"merci docteur","12:94" ,R.drawable.logopatient))
        listPat.add(ModelDoctorMessage("Mouadh" ,"merci docteur","12:94" ,R.drawable.logopatient))
        listPat.add(ModelDoctorMessage("Mouadh" ,"merci docteur","12:94" ,R.drawable.logopatient))
        listPat.add(ModelDoctorMessage("Mouadh" ,"merci docteur","12:94" ,R.drawable.logopatient))
        listPat.add(ModelDoctorMessage("Mouadh" ,"merci docteur","12:94" ,R.drawable.logopatient))
        listPat.add(ModelDoctorMessage("Mouadh" ,"merci docteur","12:94" ,R.drawable.logopatient))
        listviewPatient!!.adapter= MyAdapterDoctorMessage(requireContext(),R.layout.message_patients_list_for_doctor,listPat)

        return view

    }

}
