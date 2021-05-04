package com.example.stagepfe.Fragments.Doctor

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import com.example.stagepfe.Activity.Doctors.AddOrdonanceDoctorActivity
import com.example.stagepfe.Activity.Doctors.AddRapportDoctorActivity
import com.example.stagepfe.Adapters.Doctor.MyAdapterRapport
import com.example.stagepfe.Adapters.Doctor.MyAdapterShowOrdonnancePatForDoc
import com.example.stagepfe.Models.Doctors.ModelRapport
import com.example.stagepfe.Models.Doctors.ModelShowOrdonnancePatForDoctor
import com.example.stagepfe.R


class ShowRapportPatientForDoctorFragment : Fragment() {

    var listviewRapport: ListView? = null
    var listRapport = mutableListOf<ModelRapport>()
    var addRapport: ImageView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_show_rapport_patient_for_doctor, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        addRapport = view.findViewById(R.id.Add_Rapport)
        listviewRapport =view.findViewById<ListView>(R.id.showRapportPatForDoctorr)
        listviewRapport!!.adapter = MyAdapterRapport(requireContext(), R.layout.list_rapport, listRapport)

        addRapport!!.setOnClickListener {
            var intent = Intent(activity, AddRapportDoctorActivity::class.java)
            startActivity(intent)

        }
    }


}