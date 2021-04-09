package com.example.stagepfe.Fragments.Patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Adapters.Patients.MyAdapterRdvPatient
import com.example.stagepfe.Models.Patient.ModelMessagePatient
import com.example.stagepfe.Models.Patient.ModelRDVPatient
import com.example.stagepfe.R


class RendezVousPatientFragment : Fragment() {
    var listRDVPatient: ListView? = null
    var list = mutableListOf<ModelRDVPatient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_rendez_vous, container, false)

initView(view)
        return view
    }

    private fun initView(view: View) {
        listRDVPatient = view.findViewById<ListView>(R.id.List_RDV)


        list.add(ModelRDVPatient("11/05/2021","11:00", "termineé", R.color.green))
        list.add(ModelRDVPatient("11/05/2021","11:00", "termineé", R.color.green))
        list.add(ModelRDVPatient("11/05/2021","11:00", "pas encore", R.color.red))
        list.add(ModelRDVPatient("11/05/2021","11:00", "termineé", R.color.green))
        listRDVPatient!!.adapter = MyAdapterRdvPatient(requireContext(),R.layout.rdv_list_patient,list)


    }


}