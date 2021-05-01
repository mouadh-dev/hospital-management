package com.example.stagepfe.Adapters.Doctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.stagepfe.Models.Doctors.ModelRdvDoctor
import com.example.stagepfe.R

class MyAdapterRdvDoctor(var mCtx: Context, var resources:Int, var items:List<ModelRdvDoctor>): ArrayAdapter<ModelRdvDoctor>(mCtx, resources, items)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var TvetatRendezVous: TextView = view.findViewById(R.id.TvetatRendezVous)
        var TVdateRendezVous: TextView = view.findViewById(R.id.TVdateRendezVous)
        var TVheureRendezVous: TextView = view.findViewById(R.id.TVheureRendezVous)
        var colorCheckRDV: LinearLayout = view.findViewById(R.id.Color_Check_RDV_Doctor)
        var namePatient: TextView = view.findViewById(R.id.name_Patient)


        var mItem: ModelRdvDoctor = items[position]
        TvetatRendezVous.text = mItem.date_rdv
        TVdateRendezVous.text = mItem.heure_rdv
        TVheureRendezVous.text = mItem.etat_rdv
        colorCheckRDV.setBackgroundColor(mCtx.resources.getColor( mItem.color_Check_RDV_Doc))
        namePatient.text = mItem.namePatient
        return view
    }
}