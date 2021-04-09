package com.example.stagepfe.Adapters.Patients

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.stagepfe.Models.Patient.ModelNotificationPatient
import com.example.stagepfe.Models.Patient.ModelRDVPatient
import com.example.stagepfe.R

class MyAdapterRdvPatient(var mCtx: Context, var resources:Int, var items:List<ModelRDVPatient>): ArrayAdapter<ModelRDVPatient>(mCtx,resources,items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)


        var textDateRDV: TextView = view.findViewById(R.id.Date_RDV)
        var textHourRDV: TextView = view.findViewById(R.id.Hour_RDV)
        var textCheckRDV: TextView = view.findViewById(R.id.Check_RDV)
        var colorCheckRDV: LinearLayout = view.findViewById(R.id.Color_Check_RDV)


        var items: ModelRDVPatient = items[position]
        textDateRDV.text = items.datRDV
        textHourRDV.text = items.hourRDV
        textCheckRDV.text = items.checkRDV
        colorCheckRDV.setBackgroundColor(mCtx.resources.getColor(items.colorCheckRDV))



        return view

    }
}