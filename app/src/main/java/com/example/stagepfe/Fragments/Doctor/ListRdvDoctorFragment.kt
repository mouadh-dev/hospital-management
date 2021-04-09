package com.example.stagepfe.Fragments.Doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Models.Doctors.ModelRdvDocteur
import com.example.stagepfe.Adapters.Doctor.MyAdapterRdvDoctor
import com.example.stagepfe.R

class ListRdvDoctorFragment : Fragment() {
    var listviewRdv: ListView? = null
    var listRdvDoc = mutableListOf<ModelRdvDocteur>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       var view=inflater.inflate(R.layout.fragment_list_rdv_doctor, container, false)

        initView(view)
        return view
    }

    private fun initView(view: View) {
        listviewRdv = view.findViewById<ListView>(R.id.listRdvDocteur)
        listRdvDoc.add(ModelRdvDocteur("Rendez-Vous", "11-03-2020 ", "11:00", "Terminer"))
        listviewRdv!!.adapter= MyAdapterRdvDoctor(requireContext(),R.layout.list_rdv_for_doctor, listRdvDoc)

    }
}