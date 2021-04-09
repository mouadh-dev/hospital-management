package com.example.stagepfe.Fragments.Doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.example.stagepfe.Models.Doctors.ModelNotification
import com.example.stagepfe.Models.Doctors.ModelRdvDocteur
import com.example.stagepfe.R


class ProfilDoctorMyRdvFragment : Fragment() {
    var listviewDoctorProfilRdv: ListView? = null
    var listDoctorProfilRdv = mutableListOf<ModelRdvDocteur>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      var view= inflater.inflate(R.layout.fragment_profil_doctor_my_rdv, container, false)
        listDoctorProfilRdv.add(ModelRdvDocteur("Rendez-Vous","12/12/2021","12:35","Terminer"))
        listDoctorProfilRdv.add(ModelRdvDocteur("Rendez-Vous","12/12/2021","12:35","Terminer"))
        listDoctorProfilRdv.add(ModelRdvDocteur("Rendez-Vous","12/12/2021","12:35","Terminer"))
        listDoctorProfilRdv.add(ModelRdvDocteur("Rendez-Vous","12/12/2021","12:35","Terminer"))
        listDoctorProfilRdv.add(ModelRdvDocteur("Rendez-Vous","12/12/2021","12:35","Terminer"))
        listDoctorProfilRdv.add(ModelRdvDocteur("Rendez-Vous","12/12/2021","12:35","Terminer"))
        listviewDoctorProfilRdv =view.findViewById<ListView>(R.id.listRdvDocteur)
        listDoctorProfilRdv
        initView(view)
        return view
    }

    private fun initView(view: View) {
    }

}