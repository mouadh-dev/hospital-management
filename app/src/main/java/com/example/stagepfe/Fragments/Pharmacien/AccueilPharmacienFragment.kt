package com.example.stagepfe.Fragments.Pharmacien

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Adapters.Doctor.MyAdapterListPatientForDoctors
import com.example.stagepfe.Adapters.Pharmacien.MyAdapterNewOrdonnancePharmacien
import com.example.stagepfe.Models.Doctors.ModelPatientList
import com.example.stagepfe.Models.Pharmacien.ModelNewOrdonnancePharmacien
import com.example.stagepfe.R


class AccueilPharmacienFragment : Fragment() {
    var listviewNewOrdonnanceParmacien: ListView? = null
    var listNewOrdonnanceParmacien = mutableListOf<ModelNewOrdonnancePharmacien>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_accueil_pharmacien, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listviewNewOrdonnanceParmacien = view.findViewById<ListView>(R.id.listNewOrdonnance_Pharmacien)
        listNewOrdonnanceParmacien.add(ModelNewOrdonnancePharmacien("Mohamed","12/12/2021","12:12",R.drawable.logopatient))
        listNewOrdonnanceParmacien.add(ModelNewOrdonnancePharmacien("Mohamed","12/12/2021","12:12",R.drawable.logopatient))
        listNewOrdonnanceParmacien.add(ModelNewOrdonnancePharmacien("Mohamed","12/12/2021","12:12",R.drawable.logopatient))
        listviewNewOrdonnanceParmacien!!.adapter = MyAdapterNewOrdonnancePharmacien(requireContext(), R.layout.nouvelle_ordonnance_pharmarcien, listNewOrdonnanceParmacien)

    }


}