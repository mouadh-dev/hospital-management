package com.example.stagepfe.Adapters.Patients

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.stagepfe.Models.Patient.ModelRDVPatient
import com.example.stagepfe.Models.Patient.ModelRapportPatient
import com.example.stagepfe.R

class MyAdapterRapportPatient(var mCtx: Context, var resources:Int, var items:List<ModelRapportPatient>) :ArrayAdapter<ModelRapportPatient>(mCtx, resources,items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var textNameDoctor: TextView = view.findViewById((R.id.Name_Doctor_Rapport))
        var textDateRapport: TextView = view.findViewById(R.id.Date_Rapport)
        var textHourRapport: TextView = view.findViewById(R.id.Hour_Rapport)

        var items: ModelRapportPatient = items[position]
        textNameDoctor.text = items.nameDoctor
        textDateRapport.text = items.dateRapport
        textHourRapport.text = items.hourRapport
        return view
    }
}