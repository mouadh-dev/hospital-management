package com.example.stagepfe.Fragments.Doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Adapters.Doctor.MyAdapterNotificationDoctor
import com.example.stagepfe.Adapters.Doctor.MyAdapterShowOrdonnancePatForDoc
import com.example.stagepfe.Models.Doctors.ModelNotification
import com.example.stagepfe.Models.Doctors.ModelShowOrdonnancePatForDoctor
import com.example.stagepfe.R


class ShowOrdonnancePatientForDoctorFragment : Fragment() {
    var listviewOrdoPatForDoctor: ListView? = null
    var listOrdoPatForDoctor = mutableListOf<ModelShowOrdonnancePatForDoctor>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
     var view =inflater.inflate(R.layout.fragment_show_ordonnance_patient_for_doctor, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listviewOrdoPatForDoctor =view.findViewById<ListView>(R.id.showOrdPatForDoctorr)
        listOrdoPatForDoctor.add(ModelShowOrdonnancePatForDoctor("12/12/2020","15:50"))
        listOrdoPatForDoctor.add(ModelShowOrdonnancePatForDoctor("12/12/2020","15:50"))
        listOrdoPatForDoctor.add(ModelShowOrdonnancePatForDoctor("12/12/2020","15:50"))
        listOrdoPatForDoctor.add(ModelShowOrdonnancePatForDoctor("12/12/2020","15:50"))
        listOrdoPatForDoctor.add(ModelShowOrdonnancePatForDoctor("12/12/2020","15:50"))
        listviewOrdoPatForDoctor!!.adapter = MyAdapterShowOrdonnancePatForDoc(requireContext(), R.layout.show_ordonnance_patient_to_doctor, listOrdoPatForDoctor)


    }


}