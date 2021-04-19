package com.example.stagepfe.Adapters.AgentLabo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.Models.AgentLabo.ModelAnalyses
import com.example.stagepfe.Models.Pharmacien.ModelNewOrdonnancePharmacien
import com.example.stagepfe.R

class MyAdapterAnalyses(var mCtx: Context, var resources:Int, var items:List<ModelAnalyses>): ArrayAdapter<ModelAnalyses>(mCtx, resources, items)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imageList: ImageView = view.findViewById(R.id.IVpatientImageList_Analyses)
        var namePatient: TextView = view.findViewById(R.id.TVnamePatientList_Analyses)
        var dateAna : TextView = view.findViewById(R.id.TVDateAnalyses)
        var tempsAna : TextView = view.findViewById(R.id.TVTimeAnalyses)

        var mItem: ModelAnalyses = items[position]
        imageList.setImageDrawable(mCtx.resources.getDrawable(mItem.picAna))
        namePatient.text = mItem.nomAna
        dateAna.text = mItem.dateAna
        tempsAna.text = mItem.tempsAna

        return view
    }
}