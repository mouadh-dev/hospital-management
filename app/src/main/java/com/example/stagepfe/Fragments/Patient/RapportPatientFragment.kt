package com.example.stagepfe.Fragments.Patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.navigation.fragment.findNavController
import com.example.stagepfe.Adapters.Patients.MyAdapterRapportPatient
import com.example.stagepfe.Models.Patient.ModelRapportPatient
import com.example.stagepfe.R


class RapportPatientFragment : Fragment() {
var listRapportPatient: ListView? = null
    var list = mutableListOf<ModelRapportPatient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_rapport_patient, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listRapportPatient = view.findViewById(R.id.List_Rapport)

        list.add(ModelRapportPatient("DR Foulen", "11/04/2020","11:00"))
        list.add(ModelRapportPatient("DR Foulen", "11/04/2020","11:00"))
        list.add(ModelRapportPatient("DR Foulen", "11/04/2020","11:00"))
        list.add(ModelRapportPatient("DR Foulen", "11/04/2020","11:00"))
        list.add(ModelRapportPatient("DR Foulen", "11/04/2020","11:00"))
        list.add(ModelRapportPatient("DR Foulen", "11/04/2020","11:00"))
        list.add(ModelRapportPatient("DR Foulen", "11/04/2020","11:00"))

        listRapportPatient!!.adapter = MyAdapterRapportPatient(requireContext(),R.layout.rapport_list_patient,list)

    }


}