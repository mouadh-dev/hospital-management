package com.example.stagepfe.Fragments.Patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Adapters.Patients.MyAdapterOrdonancePatient
import com.example.stagepfe.Adapters.Patients.MyAdapterRapportPatient
import com.example.stagepfe.Models.Patient.ModelOrdonancePatient
import com.example.stagepfe.Models.Patient.ModelRapportPatient
import com.example.stagepfe.R


class OrdonancePatientFragment : Fragment() {
    var listOrdonancePatient: ListView? = null
    var list = mutableListOf<ModelOrdonancePatient>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_ordonance_patient, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listOrdonancePatient = view.findViewById(R.id.list_Ordonance_Patient)

        list.add(ModelOrdonancePatient("Ordonance 1","11/05/2020","11:00","pas encore",R.color.red,R.drawable.codebar))
        list.add(ModelOrdonancePatient("Ordonance 1","11/05/2020","11:00","pas encore",R.color.red,R.drawable.codebar))
//        list.add(ModelOrdonancePatient("Ordonance 1","11/05/2020","11:00","termineé",R.color.green,0))
        list.add(ModelOrdonancePatient("Ordonance 1","11/05/2020","11:00","pas encore",R.color.red,R.drawable.codebar,))
        list.add(ModelOrdonancePatient("Ordonance 1","11/05/2020","11:00","pas encore",R.color.red,R.drawable.codebar))
        list.add(ModelOrdonancePatient("Ordonance 1","11/05/2020","11:00","pas encore",R.color.red,R.drawable.codebar))
//        list.add(ModelOrdonancePatient("Ordonance 1","11/05/2020","11:00","pas encore",R.color.green,0))

        listOrdonancePatient!!.adapter = MyAdapterOrdonancePatient(requireContext(),R.layout.ordonance_list_patient,list)

    }


}