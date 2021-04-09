package com.example.stagepfe.Fragments.Doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Adapters.Doctor.MyAdapterPatientListForDoctorProfil
import com.example.stagepfe.Adapters.Doctor.MyAdapterRdvDoctor
import com.example.stagepfe.Models.Doctors.ModelPatientListForDoctorProfil
import com.example.stagepfe.Models.Doctors.ModelRdvDocteur
import com.example.stagepfe.R

class ProfilDoctorMyPatientFragment : Fragment() {

    var listviewDoctorProfilMyPatient: ListView? = null
    var listDoctorDoctorProfilMyPatient = mutableListOf<ModelPatientListForDoctorProfil>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view=  inflater.inflate(R.layout.fragment_profil_doctor_my_patient, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listviewDoctorProfilMyPatient =view.findViewById<ListView>(R.id.listPatientForProfilDoctor)
        listDoctorDoctorProfilMyPatient.add(ModelPatientListForDoctorProfil("Mohamed Rouahi",R.drawable.logopatient))
        listDoctorDoctorProfilMyPatient.add(ModelPatientListForDoctorProfil("Mohamed Rouahi",R.drawable.logopatient))
        listDoctorDoctorProfilMyPatient.add(ModelPatientListForDoctorProfil("Mohamed Rouahi",R.drawable.logopatient))
        listDoctorDoctorProfilMyPatient.add(ModelPatientListForDoctorProfil("Mohamed Rouahi",R.drawable.logopatient))
        listDoctorDoctorProfilMyPatient.add(ModelPatientListForDoctorProfil("Mohamed Rouahi",R.drawable.logopatient))
        listDoctorDoctorProfilMyPatient.add(ModelPatientListForDoctorProfil("Mohamed Rouahi",R.drawable.logopatient))
        listviewDoctorProfilMyPatient!!.adapter = MyAdapterPatientListForDoctorProfil(requireContext(),R.layout.list_patient_for_doctor_profil,listDoctorDoctorProfilMyPatient)

    }


}