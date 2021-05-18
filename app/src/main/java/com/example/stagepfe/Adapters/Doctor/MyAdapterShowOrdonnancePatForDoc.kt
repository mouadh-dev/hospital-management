package com.example.stagepfe.Adapters.Doctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.stagepfe.Models.Doctors.ModelShowOrdonnancePatForDoctor
import com.example.stagepfe.Models.Patient.ModelRDVPatient
import com.example.stagepfe.R
import com.example.stagepfe.entite.Ordonance
import com.example.stagepfe.entite.Rapports

class MyAdapterShowOrdonnancePatForDoc (var mCtx: Context, var resources:Int, var items:List<Ordonance>): ArrayAdapter<Ordonance>(mCtx,resources,items){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)


        var textDateRDV: TextView = view.findViewById(R.id.Date_Ord_pat_doc)
        var textHourRDV: TextView = view.findViewById(R.id.Hour_Ord_pat_doc)


        var items: Ordonance = items[position]
        textDateRDV.text = items.dateOrdonanceSend
        textHourRDV.text = items.hourOrdonanceSend




        return view

    }

    override fun getItem(position: Int): Ordonance? {
            return items[position]
    }
}