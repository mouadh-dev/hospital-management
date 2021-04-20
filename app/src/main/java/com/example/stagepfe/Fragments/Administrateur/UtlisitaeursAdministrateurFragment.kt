package com.example.stagepfe.Fragments.Administrateur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Adapters.Administrateur.MyAdapterReclamationAdministrateur
import com.example.stagepfe.Adapters.Administrateur.MyAdapterUtlisateurAdministrateur
import com.example.stagepfe.Models.Administrateur.ModelReclamationAdministrateur
import com.example.stagepfe.Models.Administrateur.ModelUtilisateursAdministrateur
import com.example.stagepfe.R


class UtlisitaeursAdministrateurFragment : Fragment() {

    var listviewUtilisateur: ListView? = null
    var listUtilisateur = mutableListOf<ModelUtilisateursAdministrateur>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_utlisitaeurs_administrateur, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listviewUtilisateur =view.findViewById<ListView>(R.id.ListUtilisateursAdministrateur)
        listUtilisateur.add(ModelUtilisateursAdministrateur("Mohamed Rouahi","",R.drawable.logopatient))
        listUtilisateur.add(ModelUtilisateursAdministrateur("Mohamed Rouahi","Dentiste",R.drawable.logopatient))
        listviewUtilisateur!!.adapter = MyAdapterUtlisateurAdministrateur(requireContext(), R.layout.utilisateur_list_administrateur, listUtilisateur)


    }


}