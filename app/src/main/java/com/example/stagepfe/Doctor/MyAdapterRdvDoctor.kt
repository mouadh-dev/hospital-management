package com.example.stagepfe.Doctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.R

class MyAdapterRdvDoctor(var mCtx: Context, var resources:Int, var items:List<ModelRdvDocteur>): ArrayAdapter<ModelRdvDocteur>(mCtx, resources, items)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var TVRendez_Vous: TextView = view.findViewById(R.id.TVRendez_Vous)
        var TvetatRendezVous: TextView = view.findViewById(R.id.TvetatRendezVous)
        var TVdateRendezVous: TextView = view.findViewById(R.id.TVdateRendezVous)
        var TVheureRendezVous: TextView = view.findViewById(R.id.TVheureRendezVous)


        var mItem: ModelRdvDocteur = items[position]
        TVRendez_Vous.text = mItem.rdv_tv
        TvetatRendezVous.text = mItem.date_rdv
        TVdateRendezVous.text = mItem.heure_rdv
        TVheureRendezVous.text = mItem.etat_rdv
        return view
    }
}