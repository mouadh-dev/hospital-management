package com.example.stagepfe.Fragments.Administrateur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Adapters.Administrateur.MyAdapterReclamationAdministrateur
import com.example.stagepfe.Adapters.Doctor.MyAdapterNotificationDoctor
import com.example.stagepfe.Models.Administrateur.ModelReclamationAdministrateur
import com.example.stagepfe.Models.Doctors.ModelNotification
import com.example.stagepfe.R


class ReclamationAdministrateurFragment : Fragment() {

    var listviewReclamation: ListView? = null
    var listReclamation = mutableListOf<ModelReclamationAdministrateur>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_reclamation_administrateur, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listviewReclamation =view.findViewById<ListView>(R.id.listRéclamationAdministrateur)
        listReclamation.add(ModelReclamationAdministrateur("Mohamed","l'assanceur du bloc A est en panne","15:29",R.drawable.logopatient))
        listReclamation.add(ModelReclamationAdministrateur("Mohamed","l'assanceur du bloc A est en panne","15:29",R.drawable.logopatient))
        listReclamation.add(ModelReclamationAdministrateur("Mohamed","l'assanceur du bloc A est en panne","15:29",R.drawable.logopatient))
        listviewReclamation!!.adapter = MyAdapterReclamationAdministrateur(requireContext(), R.layout.list_reclamation_administrateur, listReclamation)
    }


}