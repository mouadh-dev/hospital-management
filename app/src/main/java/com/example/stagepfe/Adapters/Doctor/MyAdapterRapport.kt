package com.example.stagepfe.Adapters.Doctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.stagepfe.Models.Doctors.ModelRapport
import com.example.stagepfe.R

class MyAdapterRapport(var mCtx: Context, var resources:Int, var items:List<ModelRapport>): ArrayAdapter<ModelRapport>(mCtx, resources, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)


        var nomRapporteur: TextView = view.findViewById(R.id.NameDoctorRapport)
        var specialityRapporteur: TextView = view.findViewById(R.id.SpecialityDoctorRapport)


        var mItem: ModelRapport = items[position]
        nomRapporteur.text = mItem.nom_Doctor_Rapport
        specialityRapporteur.text = mItem.speciality_Doctor_Rapport

        return view
    }
}